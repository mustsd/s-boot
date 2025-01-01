package com.github.mustsd.modules.socket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.socket.SocketEventHandler;
import com.github.mustsd.modules.socket.service.ISocketService;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.notice.mapper.SysNoticeUserMapper;
import com.github.mustsd.modules.notice.vo.UnreadCount;
import com.github.mustsd.modules.shiro.CurrentUser;
import com.github.mustsd.modules.socket.vo.Notify;
import com.github.mustsd.modules.socket.vo.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yangz
 * @date 2022-03-31 14:47
 */
@Service
public class SocketServiceImpl implements ISocketService {
  @Autowired SocketEventHandler socketEventHandler;
  @Autowired
  ISysUserService userService;
  @Resource SysNoticeUserMapper noticeUserMapper;

  @Override
  public void newNotice(String title) {
    List<UnreadCount> unreadList = noticeUserMapper.countAllUnread();
    Map<String, Object> unreadMap = new HashMap<>(unreadList.size());
    for (UnreadCount unread : unreadList) {
      unread.setTitle(title);
      unreadMap.put(unread.getAccount(), unread);
    }
    Map<String, UserSession> clientMap = socketEventHandler.getClientMap();
    for (String account : clientMap.keySet()) {
      if (unreadMap.containsKey(account)) {;
        socketEventHandler.fireEvent(
            CommonConstant.SOCKET_EVENT_UNREAD,
            clientMap.get(account).getClient(),
            unreadMap.get(account));
      }
    }
  }

  @Override
  public void notify(Notify notify) {
    String msg = notify.getMsg();
    Set<String> accounts = notify.getAccounts();
    Map<String, UserSession> clientMap = socketEventHandler.getClientMap();
    for (String account : clientMap.keySet()) {
      if (CollectionUtils.isEmpty(accounts) || notify.getAccounts().contains(account)) {;
        socketEventHandler.fireEvent(
            CommonConstant.SOCKET_EVENT_NOTIFY, clientMap.get(account).getClient(), msg);
      }
    }
  }

  @Override
  public void forceLogout(String account) {
    Map<String, UserSession> clientMap = socketEventHandler.getClientMap();
    String admin = CurrentUser.getUser().getName();
    if (clientMap.containsKey(account)) {;
      socketEventHandler.fireEvent(
          CommonConstant.SOCKET_EVENT_LOGOUT,
          clientMap.get(account).getClient(),
          "您已被 " + admin + " 强制登出");
    }
  }

  @Override
  public IPage<SysUser> listOnlineUser(IPage<SysUser> page, QueryWrapper queryWrapper) {
    Map<String, UserSession> clientMap = socketEventHandler.getClientMap();
    if (!clientMap.isEmpty()) {
      queryWrapper.in("account", clientMap.keySet());
      userService.page(page, queryWrapper);
    }
    for (SysUser user : page.getRecords()) {
      String account = user.getAccount();
      if (clientMap.containsKey(account)) {
        UserSession us = clientMap.get(account);
        user.setLoginTime(us.getCreateTime());
        user.setIp(us.getIp());
      }
    }
    return page;
  }
}
