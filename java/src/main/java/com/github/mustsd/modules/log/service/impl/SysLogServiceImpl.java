package com.github.mustsd.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.log.entity.SysLog;
import com.github.mustsd.modules.log.mapper.SysLogMapper;
import com.github.mustsd.modules.log.service.ISysLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

  @Async
  @Override
  public void saveLogAsync(SysLog log) {
    this.save(log);
  }
}
