package com.github.mustsd.common.xss;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangz
 * @date 2022-05-10 17:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
    value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {XssValidator.class})
public @interface Xss {
  String message() default "不允许任何脚本运行";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
