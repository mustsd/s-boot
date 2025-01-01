package com.github.mustsd.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.common.tree.TreeNode;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 权限
 *
 * @author yangz
 */
public interface ISysPermissionService extends IService<SysPermission> {

  /**
   * 加载菜单树
   *
   * @return
   */
  List<SysPermission> listTree();

  /**
   * 加载一级菜单
   *
   * @return
   */
  List<TreeNode> listOptions();

  /**
   * 加载角色权限树
   *
   * @param roleId
   * @return
   */
  Map<String, Object> listRolePermissionTree(String roleId);

  /**
   * 加载用户权限树
   *
   * @param userId
   * @return
   */
  Map<String, Object> listUserPermissionTree(String userId);

  /**
   * 加载用户权限
   *
   * @param userId
   * @return
   */
  Map<String, Object> listUserAuth(String userId);

  /**
   * 根据权限查询角色
   *
   * @param permissionId
   * @return
   */
  List<SysRole> listRoleByPermission(String permissionId);
}
