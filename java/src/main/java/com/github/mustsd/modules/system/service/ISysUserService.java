package com.github.mustsd.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.vo.Captcha;
import com.github.mustsd.modules.system.vo.Login;
import com.github.mustsd.modules.system.vo.PwdModify;
import com.github.mustsd.modules.system.vo.UserInfo;

import java.util.List;
import java.util.Set;

public interface ISysUserService extends IService<SysUser> {

  /**
   * 用户登录
   *
   * @param login
   * @return
   */
  UserInfo login(Login login);

  /**
   * 用户登出
   *
   * @param token
   */
  void logout(String token);

  /**
   * 根据用户主键查询关联角色
   *
   * @param id
   * @return
   */
  Set<String> getUserRoleSet(String id);

  /**
   * 根据用户主键查询关联权限
   *
   * @param id
   * @return
   */
  Set<String> getUserPermissionSet(String id);

  /**
   * @param account
   * @return
   */
  UserInfo getByAccount(String account);

  /**
   * 添加用户
   *
   * @param sysUser
   */
  void add(SysUser sysUser);

  /**
   * 查询用户角色ids
   *
   * @param id
   * @return
   */
  List<String> listRoleIds(String id);

  /**
   * 更新用户角色
   *
   * @param sysUser
   */
  void updateWithRole(SysUser sysUser);

  /**
   * 删除用户(连带用户角色)
   *
   * @param id
   */
  void removeWithRole(String id);

  /**
   * 获取验证码
   *
   * @param key
   * @return
   */
  Captcha getCaptcha(String key);

  /**
   * 根据角色查询用户
   *
   * @param roleId
   * @return
   */
  List<SysUser> listByRoleId(String roleId);

  String modifyPwd(PwdModify pwd);

  String changePassword(SysUser user);
}
