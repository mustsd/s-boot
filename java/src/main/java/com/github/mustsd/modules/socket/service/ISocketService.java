package com.github.mustsd.modules.socket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.socket.vo.Notify;

/**
 * socket interface
 *
 * @author yangz
 */
public interface ISocketService {
  IPage<SysUser> listOnlineUser(IPage<SysUser> page, QueryWrapper queryWrapper);

  void forceLogout(String account);

  void notify(Notify notify);

  void newNotice(String title);
}
