package com.github.mustsd.common.aop.annotation;

import java.lang.annotation.*;

/**
 * Log
 *
 * @author Sidong
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
  /**
   * 日志内容
   *
   * @return
   */
  String value() default "";
}
