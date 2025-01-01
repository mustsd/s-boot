package com.github.mustsd.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;

/**
 * @author yangz
 * @date 2022-03-29 10:27
 */
@org.springframework.context.annotation.Configuration
public class SocketConfig {

  /**
   * 注册netty-socket-io服务端
   *
   * @return
   */
  @Bean
  public SocketIOServer getSocketIOServer() {
    Configuration config = new Configuration();
    config.setPort(9992);
    return new SocketIOServer(config);
  }

  /**
   * tomcat启动时候，扫码socket服务器并注册
   *
   * @param socketServer
   * @return
   */
  @Bean
  public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
    return new SpringAnnotationScanner(socketServer);
  }
}
