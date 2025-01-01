package com.github.mustsd.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.entity.SysUser;
import com.github.mustsd.modules.system.vo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

  @Select(
      "select mr.* from sys_role mr "
          + "inner join sys_user_role mur on mur.role_id=mr.id "
          + "where mur.user_id=#{id}")
  List<SysRole> getUserRole(@Param("id") String id);

  @Select(
      "select mp.* from sys_permission mp "
          + "inner join sys_role_permission mrp on mrp.permission_id=mp.id "
          + "inner join sys_user_role mur on mur.role_id=mrp.role_id "
          + "where mur.user_id=#{id}")
  List<SysPermission> getUserPermission(@Param("id") String id);

  @Select(
      "select mu.id,mu.account,mu.name,mu.gender,mu.phone,mu.email from sys_user mu "
          + "inner join sys_user_role mur on mur.user_id=mu.id "
          + "where mur.role_id=#{roleId}")
  List<SysUser> listByRoleId(@Param("roleId") String roleId);

  @Select(
      "select su.*,so.name orgName from sys_user su "
          + "inner join sys_org so on so.id=su.org_id "
          + "where su.account=#{account} ")
  UserInfo getByAccount(@Param("account") String account);
}
