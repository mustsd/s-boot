package com.github.mustsd.modules.system.service.impl;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.constant.CacheConstant;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.exception.AuthException;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.common.util.*;
import com.github.mustsd.modules.log.entity.SysLoginLog;
import com.github.mustsd.modules.log.service.ISysLoginLogService;
import com.github.mustsd.modules.shiro.CurrentUser;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.entity.SysUserRole;
import com.github.mustsd.modules.system.mapper.SysUserMapper;
import com.github.mustsd.modules.system.service.ISysUserRoleService;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.system.vo.Captcha;
import com.github.mustsd.modules.system.vo.Login;
import com.github.mustsd.modules.system.vo.PwdModify;
import com.github.mustsd.modules.system.vo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements ISysUserService {

  @Autowired ISysUserRoleService userRoleService;
  @Autowired ISysLoginLogService loginLogService;
  @Autowired
  RedisUtil redisUtil;

  @Override
  public UserInfo login(Login login) {

    String key = login.getKey();
    String captcha = login.getCaptcha();
    if (StringUtils.isEmpty(captcha)) {
      throw new AuthException("验证码不能为空");
    }
    String rdsKey = DigestUtil.md5Hex(captcha + key);
    Object rdsCaptcha = redisUtil.get(CacheConstant.SYS_CAPTCHA_CACHE + rdsKey);
    if (rdsCaptcha == null || !rdsCaptcha.equals(captcha)) {
      throw new AuthException("验证码错误");
    }
    String account = login.getAccount();
    if (StringUtils.isEmpty(account)) {
      throw new AuthException("用户名不能为空");
    }
    String pwd = login.getPassword();
    if (StringUtils.isEmpty(pwd)) {
      throw new AuthException("密码不能为空");
    }
    SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account));
    if (user == null) {
      throw new AuthException("用户不存在");
    }
    if (!verifyPwd(pwd, user)) {
      throw new AuthException("密码错误");
    }
    String token = JwtUtil.sign("account", account, user.getPwd(), JwtUtil.TOKEN_EXPIRE_TIME);
    redisUtil.set(CommonConstant.USER_TOKEN_PREFIX + token, token, JwtUtil.TOKEN_EXPIRE_TIME / 500);
    UserInfo userInfo = new UserInfo();
    userInfo.setToken(token);
    BeanUtils.copyProperties(user, userInfo, "pwd");
    logLogin(user.getId(), 1);
    return userInfo;
  }

  /**
   * 记录登录日志
   *
   * @param id
   */
  private void logLogin(String id, Integer type) {
    try {
      SysLoginLog loginLog = new SysLoginLog();
      HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
      String ip = CommonUtils.getIpAddressByRequest(request);
      loginLog.setUserId(id);
      loginLog.setIp(ip);
      loginLog.setType(type);
      loginLogService.save(loginLog);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 验证密码
   *
   * @param pwd
   * @param user
   * @return
   */
  private boolean verifyPwd(String pwd, SysUser user) {
    return user.getPwd().equals(pwdEncrypt(pwd, user));
  }

  /**
   * 加密密码
   *
   * @param pwd
   * @param user
   * @return
   */
  private String pwdEncrypt(String pwd, SysUser user) {
    return PwdUtil.encrypt(user.getAccount(), pwd, user.getSalt().substring(0, 8));
  }

  @Override
  public void logout(String token) {
    // 登出前先记录登出日志
    logLogin(CurrentUser.getUserId(), 2);
    SecurityUtils.getSubject().logout();
    redisUtil.del(CommonConstant.USER_TOKEN_PREFIX + token);
  }

  @Override
  public Set<String> getUserRoleSet(String id) {
    return baseMapper.getUserRole(id).stream().map(SysRole::getCode).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getUserPermissionSet(String id) {
    return baseMapper.getUserPermission(id).stream()
        .map(SysPermission::getName)
        .collect(Collectors.toSet());
  }

  @Override
  @Cacheable(value = CacheConstant.SYS_USERS_CACHE, key = "#account")
  public UserInfo getByAccount(String account) {
    UserInfo userInfo = baseMapper.getByAccount(account);
    return userInfo;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(SysUser user) {
    String salt = RandomUtil.randomString(CommonConstant.BASE_CHAR, 8);
    user.setSalt(salt.toUpperCase());
    user.setPwd(pwdEncrypt(user.getPwd(), user));
    if (!save(user)) {
      throw new BusinessException("用户创建失败");
    }
    saveUserRole(user);
  }

  @Override
  public List<String> listRoleIds(String id) {
    if (StringUtils.isEmpty(id)) {
      return Collections.EMPTY_LIST;
    }
    return userRoleService
        .list(
            new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, id)
                .select(SysUserRole::getRoleId))
        .stream()
        .map(r -> r.getRoleId())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @CacheEvict(value = CacheConstant.SYS_USERS_CACHE, key = "#user.account")
  public void updateWithRole(SysUser user) {
    if (!updateById(user)) {
      throw new BusinessException("保存失败");
    }
    String id = user.getId();
    userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
    saveUserRole(user);
  }

  private void saveUserRole(SysUser user) {
    String roleIds = user.getRoleIds();
    if (StringUtils.hasText(roleIds)) {
      List<SysUserRole> userRoles =
          Arrays.stream(roleIds.split(","))
              .map(
                  roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    return userRole;
                  })
              .collect(Collectors.toList());
      if (userRoles.size() > 0) {
        if (!userRoleService.saveBatch(userRoles)) {
          throw new BusinessException("保存失败");
        }
      }
    }
  }

  @Override
  public void removeWithRole(String id) {
    if (!removeById(id)) {
      throw new BusinessException("保存失败");
    }
    userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
  }

  @Override
  public Captcha getCaptcha(String key) {
    AbstractCaptcha shearCaptcha = CaptchaUtil.createLineCaptcha(105, 35, 4, 15);
    String captchaCode = shearCaptcha.getCode();
    String rdsKey = DigestUtil.md5Hex(captchaCode + key);
    if (!redisUtil.set(CacheConstant.SYS_CAPTCHA_CACHE + rdsKey, captchaCode, 60)) {
      throw new BusinessException("获取验证码失败");
    }
    String base64Image = shearCaptcha.getImageBase64();
    Captcha captcha = new Captcha();
    captcha.setKey(key);
    captcha.setBase64Image(CommonConstant.BASE64_IMAGE_PREFIX + base64Image);
    return captcha;
  }

  @Override
  public List<SysUser> listByRoleId(String roleId) {

    return baseMapper.listByRoleId(roleId);
  }

  @Override
  public String modifyPwd(PwdModify pwd) {
    String originalPwd = pwd.getOriginalPwd();
    String id = CurrentUser.getUserId();
    SysUser user = this.getById(id);
    if (!StringUtils.isEmpty(originalPwd)) {
      if (!verifyPwd(originalPwd, user)) {
        throw new BusinessException("原密码错误，密码修改失败");
      }
    }
    String newPwd = pwd.getNewPwd();
    String salt = RandomUtil.randomString(CommonConstant.BASE_CHAR, 8);
    String account = user.getAccount();
    user.setSalt(salt.toUpperCase());
    user.setPwd(pwdEncrypt(newPwd, user));
    if (!this.updateById(user)) {
      throw new BusinessException("密码更新失败");
    }
    // 更新redis用户信息缓存（密码）
    redisUtil.del(CacheConstant.SYS_USERS_CACHE + "::" + account);
    String token = JwtUtil.sign("account", account, user.getPwd(), JwtUtil.TOKEN_EXPIRE_TIME);
    // 更新redis用户token缓存
    redisUtil.set(CommonConstant.USER_TOKEN_PREFIX + token, token, JwtUtil.TOKEN_EXPIRE_TIME / 500);
    return token;
  }

  /** @Description: 用户管理-修改密码 @Author: xwq @Date: 2023-02-16 016 16:00 */
  @Override
  public String changePassword(SysUser user) {
    String newPwd = user.getNewPwd();
    String salt = RandomUtil.randomString(CommonConstant.BASE_CHAR, 8);
    String account = user.getAccount();
    user.setSalt(salt.toUpperCase());
    user.setPwd(pwdEncrypt(newPwd, user));
    if (!this.updateById(user)) {
      throw new BusinessException("密码更新失败");
    }
    // 更新redis用户信息缓存（密码）
    redisUtil.del(CacheConstant.SYS_USERS_CACHE + "::" + account);
    String token = JwtUtil.sign("account", account, user.getPwd(), JwtUtil.TOKEN_EXPIRE_TIME);
    // 更新redis用户token缓存
    redisUtil.set(CommonConstant.USER_TOKEN_PREFIX + token, token, JwtUtil.TOKEN_EXPIRE_TIME / 500);
    return token;
  }
}
