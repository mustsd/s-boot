package com.github.mustsd.modules.system.vo;

import lombok.Data;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Data
public class Login {
  /** Account name */
  private String account;
  /** password */
  private String password;
  /** key */
  private String key;
  /** captcha */
  private String captcha;
}
