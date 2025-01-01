package com.github.mustsd.modules.system.entity;

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
 * @date 2022-06-07 15:04:07
 */
@Data
@TableName("sys_org")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "机构", description = "机构")
public class SysOrg implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 机构名称 */
  @ColumnWidth(15)
  @ExcelProperty("机构名称")
  @ApiModelProperty(value = "机构名称")
  java.lang.String name;

  /** 地区 */
  @Dict(dictCode = "region")
  @ColumnWidth(15)
  @ExcelProperty("地区")
  @ApiModelProperty(value = "地区")
  java.lang.String region;

  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  @ColumnWidth(15)
  @ExcelProperty("创建人")
  @ApiModelProperty(value = "创建人")
  java.lang.String createBy;

  /** 创建时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ColumnWidth(15)
  @ExcelProperty("更新时间")
  @ApiModelProperty(value = "更新时间")
  java.util.Date updateTime;
}
