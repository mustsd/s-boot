package com.github.mustsd.modules.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.modules.notice.entity.SysNotice;
import com.github.mustsd.modules.notice.entity.SysNoticeUser;
import com.github.mustsd.modules.notice.mapper.SysNoticeUserMapper;
import com.github.mustsd.modules.notice.service.ISysNoticeUserService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author manage
 * @date 2022-05-24 09:08:35
 */
@Service
public class SysNoticeUserServiceImpl extends ServiceImpl<SysNoticeUserMapper, SysNoticeUser>
    implements ISysNoticeUserService {

  @Override
  public long countUnreadNotice(String userId) {
    return this.baseMapper.countUnreadNotice(userId);
  }

  @Override
  public void markRead(String id) {
    if (!this.update(
        new LambdaUpdateWrapper<SysNoticeUser>()
            .eq(SysNoticeUser::getId, id)
            .set(SysNoticeUser::getReadFlag, 1)
            .set(SysNoticeUser::getReadTime, new Date()))) {
      throw new BusinessException("标记已读失败");
    }
  }

  @Override
  public SysNotice getUserNotice(String id) {
    return this.baseMapper.getUserNotice(id);
  }
}
