package com.github.mustsd.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Configuration
@MapperScan(value = {"com.github.mustsd.modules.**.mapper*"})
public class MybatisPlusConfig {
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 如果是不同类型的库，请不要指定DbType，其会自动判断。
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
    // interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    return interceptor;
  }
}
