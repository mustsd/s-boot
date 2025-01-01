package com.github.mustsd.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mustsd.common.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_role对象", description = "sys_role")
public class SysRole extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private String id;
  /** 角色名称 */
  @ApiModelProperty(value = "角色名称")
  private String name;
  /** 角色编码 */
  @ApiModelProperty(value = "角色编码")
  private String code;
  /** 描述 */
  @ApiModelProperty(value = "描述")
  private String description;
}
