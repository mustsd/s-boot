package com.github.mustsd.common.aop.annotation;

/**
 * 字段转义
 *
 * @author yangz
 */
public @interface DictTranslate {
  String value() default "";
}
