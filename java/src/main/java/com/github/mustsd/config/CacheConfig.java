package com.github.mustsd.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.github.mustsd.common.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mustsd
 * @date 2025-01-02
 */
@Slf4j
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

  @Autowired private LettuceConnectionFactory lettuceConnectionFactory;

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    // 设置序列化
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    om.enableDefaultTyping(DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    // 配置redisTemplate
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    RedisSerializer<?> stringSerializer = new StringRedisSerializer();
    // key序列化
    redisTemplate.setKeySerializer(stringSerializer);
    // value序列化
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    // Hash key序列化
    redisTemplate.setHashKeySerializer(stringSerializer);
    // Hash value序列化
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Override
  public CacheManager cacheManager() {
    // 配置序列化（缓存默认有效期 6小时）
    RedisCacheConfiguration config =
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6));
    RedisCacheConfiguration redisCacheConfiguration =
        config
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer()));

    Set cacheNames = new HashSet<>();

    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//    configMap.put(CacheConstant.ORG_TEMP_GOODS_CACHE, config.entryTtl(Duration.ofHours(24L)));

    RedisCacheManager cacheManager =
        RedisCacheManager.builder(
                RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory))
            .cacheDefaults(redisCacheConfiguration)
            .initialCacheNames(cacheNames)
            .withInitialCacheConfigurations(configMap)
            .transactionAware()
            .build();
    return cacheManager;
  }
}
