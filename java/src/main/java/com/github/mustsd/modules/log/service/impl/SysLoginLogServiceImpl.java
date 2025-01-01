package com.github.mustsd.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.log.entity.SysLoginLog;
import com.github.mustsd.modules.log.mapper.SysLoginLogMapper;
import com.github.mustsd.modules.log.service.ISysLoginLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog>
    implements ISysLoginLogService {}
