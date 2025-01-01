package com.github.mustsd.common.exception;

/**
 * @author mustsd
 * @date 2024-12-31
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BusinessException(String msg, Object... args) {
    super(String.format(msg, args));
  }
}
