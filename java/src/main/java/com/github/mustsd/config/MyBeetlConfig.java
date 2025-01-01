package com.github.mustsd.config;

import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * @author yangz
 * @date 2022-03-04 22:32
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class MyBeetlConfig {

  @Bean
  public GroupTemplate getGroupTemplate() throws IOException {
    StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
    Configuration cfg = Configuration.defaultConfiguration();
    GroupTemplate template = new GroupTemplate(resourceLoader, cfg);
    return template;
  }

  @Bean(name = "beetlConfig")
  public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
    BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = MyBeetlConfig.class.getClassLoader();
    }
    ClasspathResourceLoader resourceLoader =
        new ClasspathResourceLoader(classLoader, "templates/html");
    beetlGroupUtilConfiguration.setResourceLoader(resourceLoader);
    beetlGroupUtilConfiguration.init();
    beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(classLoader);
    return beetlGroupUtilConfiguration;
  }

  @Bean(name = "beetlViewResolver")
  public BeetlSpringViewResolver getBeetlSpringViewResolver(
      @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
    BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
    beetlSpringViewResolver.setSuffix(".btl");
    beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
    beetlSpringViewResolver.setOrder(0);
    beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
    return beetlSpringViewResolver;
  }
}
