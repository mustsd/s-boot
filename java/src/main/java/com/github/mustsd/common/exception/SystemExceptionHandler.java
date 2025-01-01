package com.github.mustsd.common.exception;

import com.github.mustsd.common.mvc.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * System exception handler.
 *
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@RestControllerAdvice
public class SystemExceptionHandler {

  /** Handle business exception. */
  @ExceptionHandler(BusinessException.class)
  public Result<?> handleBusinessException(BusinessException e) {
    log.error(e.getMessage(), e);
    return Result.error(e.getMessage());
  }

  /** Handle auth exception. */
  @ExceptionHandler(AuthException.class)
  public Result<?> handleAuthException(AuthException e) {
    log.error(e.getMessage(), e);
    return Result.authError(e.getMessage());
  }

  /** Database index duplicates. */
  @ExceptionHandler(DuplicateKeyException.class)
  public Result<?> handleAuthException(DuplicateKeyException e) {
    return Result.error("Data exists.");
  }

  @ExceptionHandler(Exception.class)
  public Result<?> handleException(Exception e) {
    log.error(e.getMessage(), e);
    return Result.error("Operation failed. " + e.getMessage());
  }
}
