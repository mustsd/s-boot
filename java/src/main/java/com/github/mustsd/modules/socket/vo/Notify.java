package com.github.mustsd.modules.socket.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author yangz
 * @date 2022-04-01 9:16
 */
@Data
public class Notify {
  private Set<String> accounts;
  private String msg;
}
