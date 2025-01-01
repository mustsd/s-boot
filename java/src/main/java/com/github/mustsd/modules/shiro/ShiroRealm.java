package com.github.mustsd.modules.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.util.CommonUtils;
import com.github.mustsd.common.util.JwtUtil;
import com.github.mustsd.common.util.RedisUtil;
import com.github.mustsd.common.util.SpringContextUtils;
import com.github.mustsd.modules.shiro.vo.JwtToken;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.system.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

  // 使用@Lazy 来延迟加载 ISysUserService 的代理对象，否则接口切面一律不生效
  @Autowired @Lazy
  ISysUserService userService;
  @Autowired @Lazy RedisUtil redisUtil;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    if (principals != null) {
      UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
      Set<String> roleSet = userService.getUserRoleSet(userInfo.getId());
      simpleAuthorizationInfo.setRoles(roleSet);
      Set<String> permissionSet = userService.getUserPermissionSet(userInfo.getId());
      simpleAuthorizationInfo.addStringPermissions(permissionSet);
    }
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    String token = (String) authenticationToken.getCredentials();

    if (StringUtils.isEmpty(token)) {
      log.info(
          "Token authorization failed. IP : {}",
          CommonUtils.getIpAddressByRequest(SpringContextUtils.getHttpServletRequest()));
      throw new AuthenticationException("Token is empty!");
    }
    UserInfo userInfo = checkToken(token);
    userInfo.setPwd(null);
    return new SimpleAuthenticationInfo(userInfo, token, getName());
  }

  private UserInfo checkToken(String token) {
    String account = JwtUtil.getValByKey(token, CommonConstant.ACCOUNT);
    if (account == null) {
      throw new AuthenticationException("token invalid!");
    }
    UserInfo userInfo = userService.getByAccount(account);
    if (userInfo == null) {
      throw new AuthenticationException("User does not exist!");
    }
    if (!jwtTokenRefresh(token, account, userInfo.getPwd())) {
      throw new AuthenticationException("Token invalid, please login again!");
    }
    return userInfo;
  }

  /**
   * Refresh token
   *
   * @param token
   * @param account
   * @param pwd
   * @return
   */
  private boolean jwtTokenRefresh(String token, String account, String pwd) {
    String cacheToken = (String) redisUtil.get(CommonConstant.USER_TOKEN_PREFIX + token);
    if (StringUtils.isEmpty(cacheToken)) {
      return false;
    }
    try {
      JwtUtil.verify(cacheToken, "account", account, pwd);
    } catch (Exception e) {
      if (e instanceof TokenExpiredException) {
        String newAuthorization = JwtUtil.sign("account", account, pwd, JwtUtil.TOKEN_EXPIRE_TIME);
        /** redis expire time set {@link JwtUtil.EXPIRE_TIME EXPIRE_TIME / 1000 * 2} */
        redisUtil.set(
            CommonConstant.USER_TOKEN_PREFIX + token,
            newAuthorization,
            JwtUtil.TOKEN_EXPIRE_TIME / 500);
      } else {
        log.error("**token check error**,token={} ,errMsg={}", cacheToken, e.getMessage());
        return false;
      }
    }
    return true;
  }
}
