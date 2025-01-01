package com.github.mustsd.common.exception;

/**
 * Authentication exception.
 *
 * @author mustsd
 * @date 2024-12-31
 */
public class AuthException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AuthException(String msg, Object... args) {
    super(String.format(msg, args));
  }
}
