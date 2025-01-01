package com.github.mustsd.modules.system.vo;

import lombok.Data;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Data
public class UserInfo {

  private String id;
  /** organization id */
  private String orgId;
  /** organization name */
  private String orgName;
  /** account */
  private String account;
  /** password */
  private String pwd;
  /** name */
  private String name;
  /** gender */
  private Integer gender;
  /** avatar */
  private String avatar;
  /** Phone number */
  private String phone;
  /** Email */
  private String email;
  /** token */
  private String token;
  /** unread message */
  private long unread;
}
