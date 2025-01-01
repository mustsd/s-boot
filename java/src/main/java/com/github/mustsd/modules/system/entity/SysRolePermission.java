package com.github.mustsd.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@TableName("sys_role_permission")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_role_permission对象", description = "sys_role_permission")
public class SysRolePermission implements Serializable {
  private static final long serialVersionUID = 1L;

  /** id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "id")
  private String id;
  /** 角色id */
  @ApiModelProperty(value = "角色id")
  private String roleId;
  /** 权限id */
  @ApiModelProperty(value = "权限id")
  private String permissionId;
}
