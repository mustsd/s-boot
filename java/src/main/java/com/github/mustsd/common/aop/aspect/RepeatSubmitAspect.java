package com.github.mustsd.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {

  @Pointcut("@annotation(com.github.mustsd.common.aop.annotation.RepeatSubmitIntercept)")
  public void section() {}

  @Around("section()")
  public Object around(ProceedingJoinPoint point) throws Throwable {

    return point.proceed();
  }
}
