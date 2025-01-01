package com.github.mustsd;

import cn.hutool.cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Sidong
 * @date
 */
@Slf4j
public class SimpleTest {

  @Test
  public void test01() throws Exception {
    CacheUtil.newTimedCache(100);
  }
}
