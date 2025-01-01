package com.github.mustsd.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

  private String url;

  private String accessKey;

  private String secretKey;
}
