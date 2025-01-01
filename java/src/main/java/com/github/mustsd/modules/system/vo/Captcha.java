package com.github.mustsd.modules.system.vo;

import lombok.Data;

/**
 * @author mustsd
 * @date 2022-03-26 22:44
 */
@Data
public class Captcha {

  private String key;

  private String base64Image;
}
