package com.github.mustsd.config;

import com.github.mustsd.common.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger configuration
 *
 * @author mustsd
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

  @Bean(value = "defaultApi2")
  public Docket defaultApi2() {

    Docket docket =
        new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                new ApiInfoBuilder()
                    .title("MANAGE API Document")
                    .description("Based on knife4j for developer.")
                    .termsOfServiceUrl("http://www.***.com/")
                    .contact(new Contact("mustsd", "xxx.com", "***@**.com"))
                    .version("1.0")
                    .build())
            .groupName("1.0 version")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.mustsd.modules"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(Collections.singletonList(securityScheme()));
    return docket;
  }

  private SecurityScheme securityScheme() {
    return new ApiKey(CommonConstant.ACCESS_TOKEN, CommonConstant.ACCESS_TOKEN, "header");
  }
}
