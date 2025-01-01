package com.github.mustsd.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangz
 * @date 2022-03-01 9:46
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  public static Object getBean(String name) {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextUtils.applicationContext = applicationContext;
  }

  public <T> T getBean(String name, Class<T> clazz) {
    return applicationContext.getBean(name, clazz);
  }
}
