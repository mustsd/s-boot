package com.github.mustsd.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis锁工具
 *
 * @author yangz
 * @date 2022-05-05 11:24
 */
@Component
public class RedisLockHelper {
  @Autowired RedisTemplate redisTemplate;

  /**
   * 获取锁
   *
   * @param key
   * @param seconds
   * @return
   */
  public boolean lock(String key, Long seconds) {
    return (Boolean)
        redisTemplate.execute(
            (RedisCallback)
                connection -> {
                  /** 如果不存在,那么则true,则允许执行, */
                  Boolean acquire =
                      connection.setNX(key.getBytes(), String.valueOf(key).getBytes());
                  /** 防止死锁,将其key设置过期时间 */
                  connection.expire(key.getBytes(), seconds);
                  if (acquire) {
                    return true;
                  }
                  return false;
                });
  }

  /**
   * 删除锁
   *
   * @param key
   */
  public void delete(String key) {
    redisTemplate.delete(key);
  }
}
