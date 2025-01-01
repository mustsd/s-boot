package com.github.mustsd.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.system.entity.SysUserRole;
import com.github.mustsd.modules.system.mapper.SysUserRoleMapper;
import com.github.mustsd.modules.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements ISysUserRoleService {}
