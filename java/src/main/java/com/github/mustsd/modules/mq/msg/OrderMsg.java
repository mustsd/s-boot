package com.github.mustsd.modules.mq.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liusd
 * @date
 */
@Setter
@Getter
public class OrderMsg extends BaseMsg {
  private static final long serialVersionUID = 1L;

  private String id;

  private String shopId;

  private String orderNumber;

  private Integer status;
}
