package com.github.mustsd.modules.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.log.entity.SysLog;

public interface ISysLogService extends IService<SysLog> {

  void saveLogAsync(SysLog log);
}
