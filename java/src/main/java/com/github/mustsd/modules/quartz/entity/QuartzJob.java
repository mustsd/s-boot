package com.github.mustsd.modules.quartz.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.mustsd.common.aop.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author manage
 * @date 2022-03-25 08:52:07
 */
@Data
@TableName("sys_quartz_job")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "定时任务", description = "定时任务")
public class QuartzJob implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 任务名 */
  @ColumnWidth(15)
  @ExcelProperty("任务名")
  @ApiModelProperty(value = "任务名")
  java.lang.String name;

  /** 任务类 */
  @ColumnWidth(15)
  @ExcelProperty("任务类")
  @ApiModelProperty(value = "任务类")
  java.lang.String jobClass;

  /** cron表达式 */
  @ColumnWidth(15)
  @ExcelProperty("cron表达式")
  @ApiModelProperty(value = "cron表达式")
  java.lang.String cronExpression;

  /** 参数 */
  @ColumnWidth(15)
  @ExcelProperty("参数")
  @ApiModelProperty(value = "参数")
  java.lang.String parameter;

  /** 0正常 1停止 */
  @Dict(dictCode = "jobStatus")
  @ColumnWidth(15)
  @ExcelProperty("0正常 1停止")
  @ApiModelProperty(value = "0正常 1停止")
  java.lang.Integer status;

  /** 描述 */
  @ColumnWidth(15)
  @ExcelProperty("描述")
  @ApiModelProperty(value = "描述")
  java.lang.String description;

  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  @ColumnWidth(15)
  @ExcelProperty("创建人")
  @ApiModelProperty(value = "创建人")
  java.lang.String createBy;

  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @ColumnWidth(15)
  @ExcelProperty("创建时间")
  @ApiModelProperty(value = "创建时间")
  java.util.Date createTime;

  /** 更新人 */
  @TableField(fill = FieldFill.UPDATE)
  @ColumnWidth(15)
  @ExcelProperty("更新人")
  @ApiModelProperty(value = "更新人")
  java.lang.String updateBy;

  /** 更新时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @ColumnWidth(15)
  @ExcelProperty("更新时间")
  @ApiModelProperty(value = "更新时间")
  java.util.Date updateTime;
}
