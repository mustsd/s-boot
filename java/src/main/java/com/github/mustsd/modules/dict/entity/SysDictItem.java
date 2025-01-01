package com.github.mustsd.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("sys_dict_item")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_dict_item对象", description = "字典项")
public class SysDictItem extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private java.lang.String id;
  /** 字典id */
  @ApiModelProperty(value = "字典id")
  private java.lang.String dictId;
  /** key */
  @ApiModelProperty(value = "key")
  private java.lang.String itemKey;
  /** 值 */
  @ApiModelProperty(value = "值")
  private java.lang.String itemValue;
  /** 描述 */
  @ApiModelProperty(value = "描述")
  private java.lang.String description;
  /** 字典编码 */
  @TableField(exist = false)
  private java.lang.String dictCode;
}
