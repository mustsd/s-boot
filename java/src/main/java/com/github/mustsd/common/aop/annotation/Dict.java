package com.github.mustsd.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author yangz */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

  String dictCode();

  String dictText() default "";

  String dictTable() default "";

  boolean enableExportXls() default true;
}
