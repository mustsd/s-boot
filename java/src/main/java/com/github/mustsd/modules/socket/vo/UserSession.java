package com.github.mustsd.modules.socket.vo;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Data;

import java.util.Date;

/**
 * @author yangz
 * @date 2022-03-26 17:30
 */
@Data
public class UserSession {
  private String account;
  private String ip;
  private String token;
  private Date createTime;
  private SocketIOClient client;
}
