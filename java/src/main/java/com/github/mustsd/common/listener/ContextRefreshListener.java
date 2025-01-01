package com.github.mustsd.common.listener;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired SocketIOServer socketIOServer;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    socketIOServer.start();
    log.info("socket-io server startup...");
    ApplicationContext context = event.getApplicationContext();
    Environment env = context.getEnvironment();
    String ip = null;
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    String port = env.getProperty("server.port");
    String path = env.getProperty("server.servlet.context-path");
    String info =
        "\n----------------------------------------------------------"
            + "\n Application Manage Server is running! Access URLs:"
            + "\n Local: http://localhost:{}{}"
            + "\n External: http://{}:{}{}"
            + "\n Online-doc: http://{}:{}{}/doc.html"
            + "\n ----------------------------------------------------------";
    log.info(info, port, path, ip, port, path, ip, port, path);
  }
}
