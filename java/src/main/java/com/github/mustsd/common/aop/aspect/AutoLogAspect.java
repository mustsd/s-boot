package com.github.mustsd.common.aop.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mustsd.common.aop.annotation.AutoLog;
import com.github.mustsd.common.util.CommonUtils;
import com.github.mustsd.common.util.SpringContextUtils;
import com.github.mustsd.modules.log.entity.SysLog;
import com.github.mustsd.modules.log.service.ISysLogService;
import com.github.mustsd.modules.shiro.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Log AOP
 *
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Aspect
@Component
public class AutoLogAspect {
  @Autowired ISysLogService sysLogService;

  @Pointcut("@annotation(com.github.mustsd.common.aop.annotation.AutoLog)")
  public void section() {}

  @Around("section()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    AutoLog autoLog = method.getAnnotation(AutoLog.class);
    long begin = System.currentTimeMillis();
    SysLog sysLog = new SysLog();
    sysLog.setOperation(autoLog.value());
    Parameter[] parameters = method.getParameters();
    Object[] args = point.getArgs();
    JSONArray array = new JSONArray();
    for (int i = 0; i < parameters.length; i++) {
      Object arg = args[i];
      if (arg instanceof HttpServletRequest) {
        continue;
      }
      Parameter parameter = parameters[i];
      JSONObject object = new JSONObject();
      object.put(parameter.getName(), arg);
      array.add(object);
    }
    sysLog.setParam(array.toJSONString());
    sysLog.setMethod(point.getTarget().getClass().getName() + "." + signature.getName());
    sysLog.setIp(CommonUtils.getIpAddressByRequest(SpringContextUtils.getHttpServletRequest()));
    sysLog.setUserId(CurrentUser.getUserId());
    sysLog.setCreateBy(CurrentUser.getAccount());
    Object result;
    try {
      result = point.proceed();
    } catch (Exception e) {
      sysLog.setFailure(1);
      sysLog.setError(e.getMessage());
      e.printStackTrace();
      throw e;
    } finally {
      long cost = System.currentTimeMillis() - begin;
      sysLog.setCost(cost);
      sysLogService.saveLogAsync(sysLog);
    }
    return result;
  }
}
