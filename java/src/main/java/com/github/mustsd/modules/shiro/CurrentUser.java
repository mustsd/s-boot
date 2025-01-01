package com.github.mustsd.modules.shiro;

import com.github.mustsd.modules.system.vo.UserInfo;
import org.apache.shiro.SecurityUtils;

/**
 * @author mustsd
 * @date 2022-03-03 9:38
 */
public class CurrentUser {

  public static UserInfo getUser() {
    Object user;
    user = SecurityUtils.getSubject().getPrincipal();
    if (null == user) {
      throw new RuntimeException("Not login, try again.");
    }
    return (UserInfo) user;
  }

  public static String getUserId() {
    return getUser().getId();
  }

  public static String getOrgId() {
    return getUser().getOrgId();
  }

  public static String getAccount() {
    return getUser().getAccount();
  }

  public static String getAccountName() {
    try {
      return getUser().getAccount();
    } catch (Exception e) {
      return "default";
    }
  }
}
