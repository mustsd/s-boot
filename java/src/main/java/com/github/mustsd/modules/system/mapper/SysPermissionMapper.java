package com.github.mustsd.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

  @Select(
      "select mp.*,mrp.role_id from sys_permission mp "
          + "left join sys_role_permission mrp on mrp.permission_id=mp.id "
          + "and mrp.role_id=#{roleId} order by mp.sort_no asc")
  List<SysPermission> listRolePermission(@Param("roleId") String roleId);

  @Select(
      "select mp.*,mup.user_id from sys_permission mp "
          + "left join (select DISTINCT(mrp.permission_id) permission_id,mur.user_id from sys_role_permission mrp "
          + "inner join sys_user_role mur on mur.role_id=mrp.role_id "
          + "where mur.user_id= #{userId}) mup on mup.permission_id=mp.id order by mp.sort_no asc")
  List<SysPermission> listUserAuth(@Param("userId") String userId);

  @Select(
      "select mr.* from sys_role mr "
          + "inner join sys_role_permission mrp on mrp.role_id=mr.id "
          + "where mrp.permission_id=#{permissionId}")
  List<SysRole> listRoleByPermission(@Param("permissionId") String permissionId);
}
