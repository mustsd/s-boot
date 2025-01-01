package com.github.mustsd.modules.notice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.modules.socket.service.ISocketService;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.service.ISysUserService;
import com.github.mustsd.modules.notice.entity.SysNotice;
import com.github.mustsd.modules.notice.entity.SysNoticeUser;
import com.github.mustsd.modules.notice.mapper.SysNoticeMapper;
import com.github.mustsd.modules.notice.service.ISysNoticeService;
import com.github.mustsd.modules.notice.service.ISysNoticeUserService;
import com.github.mustsd.modules.shiro.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author manage
 * @date 2022-05-24 10:26:11
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice>
    implements ISysNoticeService {

  @Autowired
  ISysUserService userService;
  @Autowired ISysNoticeUserService noticeUserService;
  @Autowired
  ISocketService socketService;

  @Override
  public void add(SysNotice sysNotice) {
    sysNotice.setStatus(1);
    this.save(sysNotice);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(String id) {
    if (!this.removeById(id)) {
      throw new BusinessException("删除失败");
    }
    long count =
        noticeUserService.count(
            new LambdaQueryWrapper<SysNoticeUser>().eq(SysNoticeUser::getNoticeId, id));
    if (count == 0) {
      return;
    }
    if (!noticeUserService.remove(
        new LambdaQueryWrapper<SysNoticeUser>().eq(SysNoticeUser::getNoticeId, id))) {
      throw new BusinessException("删除失败");
    }
  }

  @Override
  public void cancel(String id) {
    if (!this.update(
        new LambdaUpdateWrapper<SysNotice>()
            .eq(SysNotice::getId, id)
            .eq(SysNotice::getStatus, 2)
            .set(SysNotice::getStatus, 3)
            .set(SysNotice::getCancelBy, CurrentUser.getAccount())
            .set(SysNotice::getCancelTime, new Date()))) {
      throw new BusinessException("撤销失败");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void publish(String id) {
    SysNotice notice = this.getById(id);
    if (notice.getStatus().equals(2)) {
      throw new BusinessException("请勿重复发布");
    }
    Supplier<SysNoticeUser> noticeUserSupplier = SysNoticeUser::new;
    List<SysNoticeUser> noticeUsers =
        userService.list(new LambdaQueryWrapper<SysUser>().select(SysUser::getId)).stream()
            .map(
                u -> {
                  String userId = u.getId();
                  SysNoticeUser noticeUser = noticeUserSupplier.get();
                  noticeUser.setNoticeId(id);
                  noticeUser.setUserId(userId);
                  return noticeUser;
                })
            .collect(Collectors.toList());
    if (!this.update(
        new LambdaUpdateWrapper<SysNotice>()
            .eq(SysNotice::getId, id)
            .eq(SysNotice::getStatus, 1)
            .set(SysNotice::getStatus, 2)
            .set(SysNotice::getSendBy, CurrentUser.getAccount())
            .set(SysNotice::getSendTime, new Date()))) {
      throw new BusinessException("发布失败");
    }
    if (CollectionUtil.isEmpty(noticeUsers)) {
      throw new BusinessException("接收人不能为空");
    }
    if (!noticeUserService.saveBatch(noticeUsers)) {
      throw new BusinessException("保存接收人信息失败");
    }
    // 推送通知提醒
    socketService.newNotice(notice.getTitle());
  }

  @Override
  public IPage pageMyNotice(Page<SysNotice> page, QueryWrapper<SysNotice> queryWrapper) {
    return this.baseMapper.pageMyNotice(page, queryWrapper);
  }

  @Override
  public SysNotice readNotice(SysNotice sysNotice) {
    SysNotice notice = noticeUserService.getUserNotice(sysNotice.getNoticeUserId());
    if (notice.getReadFlag().equals(0)) {
      noticeUserService.markRead(notice.getNoticeUserId());
    }
    return notice;
  }
}
