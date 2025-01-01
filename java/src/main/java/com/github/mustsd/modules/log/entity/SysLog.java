package com.github.mustsd.modules.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mustsd.common.aop.annotation.Dict;
import com.github.mustsd.common.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("sys_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_log对象", description = "操作日志")
public class SysLog extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private java.lang.String id;
  /** 用户id */
  @ApiModelProperty(value = "用户id")
  private java.lang.String userId;
  /** 行为 */
  @ApiModelProperty(value = "行为")
  private java.lang.String operation;
  /** 调用方法 */
  @ApiModelProperty(value = "调用方法")
  private java.lang.String method;
  /** IP */
  @ApiModelProperty(value = "IP")
  private java.lang.String ip;
  /** 请求参数 */
  @ApiModelProperty(value = "请求参数")
  private java.lang.String param;
  /** 操作耗时 */
  @ApiModelProperty(value = "操作耗时")
  private java.lang.Long cost;
  /** 操作失败 */
  @Dict(dictCode = "operationFailed")
  @ApiModelProperty(value = "操作失败")
  private java.lang.Integer failure;
  /** 失败原因 */
  @ApiModelProperty(value = "失败原因")
  private java.lang.String error;

  @TableField(exist = false)
  private java.lang.String updateBy;

  @TableField(exist = false)
  private java.util.Date updateTime;
}
