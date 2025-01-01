package com.github.mustsd.modules.file.entity;

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
 * @date 2022-03-22 15:45:09
 */
@Data
@TableName("sys_file")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OSS文件", description = "OSS文件")
public class SysFile implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  String id;

  /** 名称 */
  @ColumnWidth(15)
  @ExcelProperty("名称")
  @ApiModelProperty(value = "名称")
  String name;

  /** 桶 */
  @Dict(dictCode = "bucket")
  @ColumnWidth(15)
  @ExcelProperty("桶")
  @ApiModelProperty(value = "桶")
  String bucket;

  /** 文件路径 */
  @ColumnWidth(15)
  @ExcelProperty("文件路径")
  @ApiModelProperty(value = "文件路径")
  String path;

  /** 文件大小 */
  @ColumnWidth(15)
  @ExcelProperty("文件大小")
  @ApiModelProperty(value = "文件大小")
  java.math.BigDecimal fileSize;

  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  @ColumnWidth(15)
  @ExcelProperty("创建人")
  @ApiModelProperty(value = "创建人")
  String createBy;

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
  String updateBy;

  /** 更新时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @ColumnWidth(15)
  @ExcelProperty("更新时间")
  @ApiModelProperty(value = "更新时间")
  java.util.Date updateTime;
}
