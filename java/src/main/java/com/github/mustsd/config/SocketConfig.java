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
   *  Register netty-socket-io server
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
   * When tomcat starting, scan socket server and register.
   *
   * @param socketServer
   * @return
   */
  @Bean
  public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
    return new SpringAnnotationScanner(socketServer);
  }
}
