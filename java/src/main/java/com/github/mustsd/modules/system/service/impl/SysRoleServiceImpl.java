package com.github.mustsd.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.mapper.SysRoleMapper;
import com.github.mustsd.modules.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements ISysRoleService {}
