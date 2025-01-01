package com.github.mustsd.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Http request configuration.
 *
 * @author mustsd
 */
@Configuration
@Slf4j
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate
        .getMessageConverters()
        .forEach(
            it -> {
              if (it instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter converter = (StringHttpMessageConverter) it;
                converter.setDefaultCharset(StandardCharsets.UTF_8);
              }
            });
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setReadTimeout(5000);
    factory.setConnectTimeout(15000);
    PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    manager.setMaxTotal(1000);
    manager.setDefaultMaxPerRoute(500);
    factory.setHttpClient(
        HttpClientBuilder.create()
            .setRetryHandler(
                new DefaultHttpRequestRetryHandler(3, true, new ArrayList<>()) {
                  @Override
                  public boolean retryRequest(
                      IOException exception, int executionCount, HttpContext context) {
                    log.error("retry...");
                    return super.retryRequest(exception, executionCount, context);
                  }
                })
            .setConnectionManager(manager)
            .build());
    restTemplate.setRequestFactory(factory);
    return restTemplate;
  }
}
