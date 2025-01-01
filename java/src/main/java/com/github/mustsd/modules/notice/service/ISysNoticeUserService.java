package com.github.mustsd.modules.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.notice.entity.SysNotice;
import com.github.mustsd.modules.notice.entity.SysNoticeUser;

/**
 * @author manage
 * @date 2022-05-24 09:08:35
 */
public interface ISysNoticeUserService extends IService<SysNoticeUser> {

  /**
   * 用户未读消息数
   *
   * @param userId
   * @return
   */
  long countUnreadNotice(String userId);

  /**
   * 标记已读
   *
   * @param id
   */
  void markRead(String id);

  /**
   * 查询用户消息
   *
   * @param id
   * @return
   */
  SysNotice getUserNotice(String id);
}
