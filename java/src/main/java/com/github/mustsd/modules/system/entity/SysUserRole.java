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
@TableName("sys_user_role")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_user_role对象", description = "sys_user_role")
public class SysUserRole implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private String id;
  /** 用户id */
  @ApiModelProperty(value = "用户id")
  private String userId;
  /** 角色id */
  @ApiModelProperty(value = "角色id")
  private String roleId;
}
