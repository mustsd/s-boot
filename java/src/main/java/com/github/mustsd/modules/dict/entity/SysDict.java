package com.github.mustsd.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mustsd.common.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("sys_dict")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_dict对象", description = "字典")
public class SysDict extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private java.lang.String id;
  /** 角色名称 */
  @ApiModelProperty(value = "字典名称")
  private java.lang.String name;
  /** 角色编码 */
  @ApiModelProperty(value = "字典编码")
  private java.lang.String code;
  /** 描述 */
  @ApiModelProperty(value = "描述")
  private java.lang.String description;
}
