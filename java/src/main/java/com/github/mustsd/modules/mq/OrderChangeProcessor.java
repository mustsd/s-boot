//package com.github.mustsd.modules.mq;
//
//import com.github.mustsd.modules.mq.msg.OrderMsg;
//import com.github.mustsd.config.RabbitMqConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//
///**
// * @author Sidong
// * @date 2022-06-20
// */
//@Slf4j
//@Component
//public class OrderChangeProcessor {
//
//  @Autowired BaseSender baseSender;
//
//  private static final Long DEFAULT_DELAY = 30 * 1000L;
//
//  @RabbitListener(queues = RabbitMqConfig.ORDER_DELAY_QUEUE, concurrency = "1")
//  public void processMsg(
//      OrderMsg msg,
//      @Header(AmqpHeaders.RECEIVED_EXCHANGE) String exchange,
//      @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routeKey) {
//
//    try {
//      process(msg);
//    } catch (Exception e) {
//      e.printStackTrace();
//
//      retry(msg);
//    }
//  }
//
//  public void process(OrderMsg msg) {
//    // Process business ...
//  }
//
//  public void retry(OrderMsg msg) {
//    if (msg.reentry()) {
//      baseSender.sendOrderChangeTask(msg, msg.getCount().intValue() * DEFAULT_DELAY);
//    } else {
//      log.warn("orderChangeQueue retry times run out，orderNumber={}", msg.getOrderNumber());
//    }
//  }
//}
