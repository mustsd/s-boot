package com.github.mustsd.common.mvc.vo;

import com.github.mustsd.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Data
public class Result<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  /** Success flag */
  private boolean success = true;

  /** Message */
  private String message = "Operation successful!";

  /** Return code */
  private Integer code = 0;

  /** Return data */
  private T result;

  /** Time stamp. */
  private long timestamp = System.currentTimeMillis();

  public Result() {}

  public Result<T> success(String message) {
    this.message = message;
    this.code = CommonConstant.RES_OK;
    this.success = true;
    return this;
  }

  public static Result<Object> ok() {
    Result<Object> r = new Result<Object>();
    r.setSuccess(true);
    r.setCode(CommonConstant.RES_OK);
    r.setMessage("Success");
    return r;
  }

  public static Result<Object> ok(String msg) {
    Result<Object> r = new Result<Object>();
    r.setSuccess(true);
    r.setCode(CommonConstant.RES_OK);
    r.setMessage(msg);
    return r;
  }

  public static Result<Object> ok(Object data) {
    Result<Object> r = new Result<Object>();
    r.setSuccess(true);
    r.setCode(CommonConstant.RES_OK);
    r.setResult(data);
    return r;
  }

  /**
   * Auth error.
   *
   * @param msg
   * @return
   */
  public static Result<Object> authError(String msg) {
    return error(CommonConstant.RES_NO_AUTH, msg);
  }

  /**
   * Error.
   *
   * @param msg
   * @return
   */
  public static Result<Object> error(String msg) {
    return error(CommonConstant.RES_ERROR, msg);
  }

  public static Result<Object> error(int code, String msg) {
    Result<Object> r = new Result<Object>();
    r.setCode(code);
    r.setMessage(msg);
    r.setSuccess(false);
    return r;
  }
}
