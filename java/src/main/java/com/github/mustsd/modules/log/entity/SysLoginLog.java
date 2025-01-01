package com.github.mustsd.modules.log.entity;

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
@TableName("sys_login_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_login_log对象", description = "登录日志")
public class SysLoginLog extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private java.lang.String id;
  /** 用户id */
  @ApiModelProperty(value = "用户id")
  private java.lang.String userId;
  /** IP */
  @ApiModelProperty(value = "IP")
  private java.lang.String ip;
  /** 操作 1、登录 2、登出 */
  @ApiModelProperty(value = "操作 1、登录 2、登出")
  private java.lang.Integer type;

  @TableField(exist = false)
  private java.lang.String updateBy;

  @TableField(exist = false)
  private java.util.Date updateTime;
}
