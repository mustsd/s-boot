package com.github.mustsd.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liusd
 * @date 2022-06-20 1:59 下午
 */
@Configuration
public class RabbitMqConfig {

  /** 普通交换机 */
  public static final String BASE_EXCHANGE = "aistock.exchange.base";
  /** 延时交换机 */
  public static final String DELAY_EXCHANGE = "aistock.exchange.delay";
  /** 延时任务队列 */
  public static final String BASE_DELAY_QUEUE = "aistock.queue.delay.base";
  /** 普通任务队列 */
  public static final String BASE_QUEUE = "aistock.queue.base";

  /** 订单状态变化队列 */
  public static final String ORDER_DELAY_QUEUE = "aistock.queue.delay.orderChange";

  /** 基础交换机配置 */
  @Bean
  public DirectExchange baseExchange() {
    return new DirectExchange(BASE_EXCHANGE, true, false, null);
  }

  /** 延时交换机配置 */
  @Bean
  public CustomExchange delayExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
  }

  /** 基础队列配置 */
  @Bean
  public Queue baseQueue() {
    return new Queue(BASE_QUEUE, true, false, false);
  }

  @Bean
  public Binding baseQueueBinding(Queue baseQueue, DirectExchange baseExchange) {
    return BindingBuilder.bind(baseQueue).to(baseExchange).with(BASE_QUEUE);
  }

  @Bean
  public Queue baseDelayQueue() {
    return new Queue(BASE_DELAY_QUEUE, true, false, false);
  }

  @Bean
  public Binding baseDelayQueueBinding(Queue baseDelayQueue, CustomExchange delayExchange) {
    return BindingBuilder.bind(baseDelayQueue).to(delayExchange).with(BASE_DELAY_QUEUE).noargs();
  }

  @Bean
  public Queue orderChangeDelayQueue() {
    return new Queue(ORDER_DELAY_QUEUE, true, false, false);
  }

  @Bean
  public Binding orderChangeDelayQueueBinding(
      Queue orderChangeDelayQueue, CustomExchange delayExchange) {
    return BindingBuilder.bind(orderChangeDelayQueue)
        .to(delayExchange)
        .with(ORDER_DELAY_QUEUE)
        .noargs();
  }
}
