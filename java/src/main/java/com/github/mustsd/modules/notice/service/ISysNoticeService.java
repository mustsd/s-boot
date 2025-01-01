package com.github.mustsd.modules.notice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.notice.entity.SysNotice;

/**
 * @author manage
 * @date 2022-05-24 10:26:11
 */
public interface ISysNoticeService extends IService<SysNotice> {

  /**
   * 添加
   *
   * @param sysNotice
   */
  void add(SysNotice sysNotice);

  /**
   * 删除
   *
   * @param id
   */
  void delete(String id);
  /**
   * 撤销通知
   *
   * @param id
   */
  void cancel(String id);

  /**
   * 发布通知
   *
   * @param id
   */
  void publish(String id);

  /**
   * 分页查询我的消息
   *
   * @param page
   * @param queryWrapper
   * @return
   */
  IPage pageMyNotice(Page<SysNotice> page, QueryWrapper<SysNotice> queryWrapper);

  SysNotice readNotice(SysNotice sysNotice);
}
