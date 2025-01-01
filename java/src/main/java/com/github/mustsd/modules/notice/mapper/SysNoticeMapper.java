package com.github.mustsd.modules.notice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mustsd.modules.notice.entity.SysNotice;
import org.apache.ibatis.annotations.Param;

/**
 * @author manage
 * @date 2022-05-24 10:26:11
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

  IPage pageMyNotice(
      @Param("page") Page<SysNotice> page,
      @Param(Constants.WRAPPER) QueryWrapper<SysNotice> queryWrapper);
}
