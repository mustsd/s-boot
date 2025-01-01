package com.github.mustsd.config;

import com.github.mustsd.config.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangz
 * @date 2022-03-22 14:18
 */
@Slf4j
@Configuration
public class MinIoConfig {

  @Autowired
  MinioProperties properties;

  @Bean
  public MinioClient getMinIoClient() {

    MinioClient minioClient = null;
    try {
      minioClient =
          MinioClient.builder()
              .credentials(properties.getAccessKey(), properties.getSecretKey())
              .endpoint(properties.getUrl())
              .build();
      log.info("MinIo 启动成功, url={}", properties.getUrl());
    } catch (Exception e) {
      log.error("MinIo 启动失败,msg={}", e.getMessage());
    }
    return minioClient;
  }
}
