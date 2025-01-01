package com.github.mustsd.common.aop.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防抖拦截注解
 *
 * @author yangz
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmitIntercept {

  @AliasFor("interval")
  long value() default 3000L;

  /** 最大阻挡时间，默认5s */
  long interval() default 3000L;
}
