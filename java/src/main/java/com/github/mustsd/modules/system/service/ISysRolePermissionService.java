package com.github.mustsd.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.system.entity.SysRolePermission;
import com.github.mustsd.modules.system.vo.GrantPerm;

public interface ISysRolePermissionService extends IService<SysRolePermission> {
  /**
   * 角色赋权
   *
   * @param grantPerm
   */
  void grantPerm(GrantPerm grantPerm);
}
