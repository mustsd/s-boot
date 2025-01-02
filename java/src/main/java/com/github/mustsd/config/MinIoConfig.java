package com.github.mustsd.config;

import com.github.mustsd.config.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mustsd
 * @date 2025-01-02
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
      log.info("MinIo started successfully, url={}", properties.getUrl());
    } catch (Exception e) {
      log.error("MinIo started failed, msg={}", e.getMessage());
    }
    return minioClient;
  }
}
