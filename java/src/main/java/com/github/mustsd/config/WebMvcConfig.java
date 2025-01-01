package com.github.mustsd.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yangz
 * @date 2022-02-24 15:34
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    /* 是否允许请求带有验证信息 */
    corsConfiguration.setAllowCredentials(true);
    /* 允许访问的客户端域名 */
    corsConfiguration.addAllowedOrigin("*");
    /* 允许服务端访问的客户端请求头 */
    corsConfiguration.addAllowedHeader("*");
    /* 允许访问的方法名,GET POST等 */
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("doc.html");
  }

  /**
   * 启动spring-boot-actuator的httptrace端点
   *
   * @return
   */
  @Bean
  public HttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }
}
