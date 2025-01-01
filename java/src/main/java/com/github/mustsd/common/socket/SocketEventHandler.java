package com.github.mustsd.common.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.util.JwtUtil;
import com.github.mustsd.modules.socket.vo.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class SocketEventHandler {

  @Autowired SocketIOServer server;
  Map<String, UserSession> clientMap = new ConcurrentHashMap<>();

  public Map<String, UserSession> getClientMap() {
    return clientMap;
  }

  @OnConnect
  public void onConnect(SocketIOClient client) {
    String token = client.getHandshakeData().getSingleUrlParam("token");
    if (StringUtils.isEmpty(token)) {
      client.disconnect();
      return;
    }
    String account = JwtUtil.getValByKey(token, CommonConstant.ACCOUNT);
    if (account == null) {
      client.disconnect();
      return;
    }

    String address = client.getHandshakeData().getAddress().getAddress().getHostAddress();
    List<String> ipList = client.getHandshakeData().getHttpHeaders().getAll("x-real-ip");
    if (!ipList.isEmpty()) {
      address = ipList.get(0);
    }

    // Login user
    if (clientMap.containsKey(account) && !clientMap.get(account).getToken().equals(token)) {
      StringBuilder msg = new StringBuilder();
      msg.append("Account logged on IP = ");
      msg.append(address);
      fireEvent(
          CommonConstant.SOCKET_EVENT_LOGOUT, clientMap.get(account).getClient(), msg.toString());
    }
    UserSession userSession = new UserSession();
    userSession.setAccount(account);
    userSession.setToken(token);
    userSession.setIp(address);
    userSession.setClient(client);
    userSession.setCreateTime(new Date());
    clientMap.put(account, userSession);
    log.info(
        "[webSocket message] New connection. user = {} ...IP = {} Online = {}", account, address, clientMap.size());
  }

  @OnDisconnect
  public void onDisconnect(SocketIOClient client) {
    String token = client.getHandshakeData().getSingleUrlParam("token");
    if (StringUtils.isEmpty(token)) {
      return;
    }
    String account = JwtUtil.getValByKey(token, CommonConstant.ACCOUNT);
    if (account == null) {
      return;
    }
    if (clientMap.containsKey(account) && clientMap.get(account).getToken().equals(token)) {
      clientMap.remove(account);
      log.info("[webSocket message] Lost connection. user = {} offline... Online = {}", account, clientMap.size());
    }
  }

  @OnEvent(value = "message")
  public void onEvent(SocketIOClient client, AckRequest request, Object data) {
    log.info("message... {}", data);
  }

  /**
   * Push a message to all users.
   *
   * @param event
   * @param data
   */
  public void fireEvent(String event, SocketIOClient client, Object data) {
    client.sendEvent(event, data);
  }
}
