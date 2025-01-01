package com.github.mustsd.modules.mq.msg;

import lombok.Getter;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liusd
 * @date 2022-06-20 2:07 下午
 */
public class BaseMsg implements Serializable {

  private static final long serialVersionUID = 2520502534300880536L;

  public int LIMIT = 10;
  @Getter private AtomicInteger count = new AtomicInteger(0);

  public void setLIMIT(int LIMIT) {
    this.LIMIT = LIMIT;
  }

  public boolean reentry() {
    return count.incrementAndGet() <= LIMIT;
  }
}
