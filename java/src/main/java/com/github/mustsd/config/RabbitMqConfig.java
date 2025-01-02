package com.github.mustsd.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mustsd
 * @date 2022-06-20 1:59 PM
 */
@Configuration
public class RabbitMqConfig {

  /** base exchange */
  public static final String BASE_EXCHANGE = "sboot.exchange.base";
  /** delay exchange */
  public static final String DELAY_EXCHANGE = "sboot.exchange.delay";
  /** delay task queue */
  public static final String BASE_DELAY_QUEUE = "sboot.queue.delay.base";
  /** base queue */
  public static final String BASE_QUEUE = "sboot.queue.base";

  /** base exchange config */
  @Bean
  public DirectExchange baseExchange() {
    return new DirectExchange(BASE_EXCHANGE, true, false, null);
  }

  /** delay exchange config */
  @Bean
  public CustomExchange delayExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, args);
  }

  /** base queue config */
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
//  @Bean
//  public Queue orderChangeDelayQueue() {
//    return new Queue(ORDER_DELAY_QUEUE, true, false, false);
//  }
//
//  @Bean
//  public Binding orderChangeDelayQueueBinding(
//      Queue orderChangeDelayQueue, CustomExchange delayExchange) {
//    return BindingBuilder.bind(orderChangeDelayQueue)
//        .to(delayExchange)
//        .with(ORDER_DELAY_QUEUE)
//        .noargs();
//  }
}
