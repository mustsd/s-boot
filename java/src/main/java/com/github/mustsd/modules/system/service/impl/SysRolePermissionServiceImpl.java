package com.github.mustsd.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.modules.system.entity.SysRolePermission;
import com.github.mustsd.modules.system.mapper.SysRolePermissionMapper;
import com.github.mustsd.modules.system.service.ISysRolePermissionService;
import com.github.mustsd.modules.system.vo.GrantPerm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRolePermissionServiceImpl
    extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
    implements ISysRolePermissionService {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void grantPerm(GrantPerm grantPerm) {
    String roleId = grantPerm.getRoleId();
    this.remove(
        new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
    List<SysRolePermission> rolePermissions =
        grantPerm.getPermIds().stream()
            .map(
                permissionId -> {
                  SysRolePermission rolePermission = new SysRolePermission();
                  rolePermission.setRoleId(roleId);
                  rolePermission.setPermissionId(permissionId);
                  return rolePermission;
                })
            .collect(Collectors.toList());
    if (rolePermissions != null && rolePermissions.size() > 0) {
      if (!this.saveBatch(rolePermissions)) {
        throw new BusinessException("保存失败");
      }
    }
  }
}
