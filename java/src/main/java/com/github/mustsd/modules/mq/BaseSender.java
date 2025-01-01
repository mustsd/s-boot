package com.github.mustsd.modules.mq;

import com.github.mustsd.config.RabbitMqConfig;
import com.github.mustsd.modules.mq.msg.OrderMsg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sidong
 * @date 2022-06-20 14:16
 */
@Component
public class BaseSender {

  @Autowired private AmqpTemplate amqpTemplate;

  public void sendBaseTask(Object task) {
    amqpTemplate.convertAndSend(RabbitMqConfig.BASE_EXCHANGE, RabbitMqConfig.BASE_QUEUE, task);
  }

  public void sendBaseDelayTask(Object task, final long delayTime) {
    sendDelay(RabbitMqConfig.BASE_DELAY_QUEUE, task, delayTime);
  }

  /** 订单状态变更消息 */
  public void sendOrderChangeTask(OrderMsg msg, final long delayTime) {
    sendDelay(RabbitMqConfig.ORDER_DELAY_QUEUE, msg, delayTime);
  }

  private void sendDelay(String routingKey, Object msg, long delayMs) {
    amqpTemplate.convertAndSend(
        RabbitMqConfig.DELAY_EXCHANGE,
        routingKey,
        msg,
        (message) -> {
          if (delayMs > 0) {
            message.getMessageProperties().setHeader("x-delay", String.valueOf(delayMs));
          }
          return message;
        });
  }
}
