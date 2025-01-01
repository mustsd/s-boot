package com.github.mustsd.modules.notice.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
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
 * @date 2022-05-24 10:26:10
 */
@Data
@TableName("sys_notice")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "系统通知", description = "系统通知")
public class SysNotice implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 标题 */
  @ColumnWidth(15)
  @ExcelProperty("标题")
  @ApiModelProperty(value = "标题")
  java.lang.String title;

  /** 状态 */
  @Dict(dictCode = "noticeStatus")
  @ColumnWidth(15)
  @ExcelProperty("状态")
  @ApiModelProperty(value = "状态")
  java.lang.Integer status;

  /** 内容 */
  @ColumnWidth(15)
  @ExcelProperty("内容")
  @ApiModelProperty(value = "内容")
  java.lang.String content;

  /** 发布人 */
  @ColumnWidth(15)
  @ExcelProperty("发布人")
  @ApiModelProperty(value = "发布人")
  java.lang.String sendBy;

  /** 发布时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ColumnWidth(15)
  @ExcelProperty("发布时间")
  @ApiModelProperty(value = "发布时间")
  java.util.Date sendTime;

  /** 撤销人 */
  @ColumnWidth(15)
  @ExcelProperty("撤销人")
  @ApiModelProperty(value = "撤销人")
  java.lang.String cancelBy;

  /** 撤销时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @ColumnWidth(15)
  @ExcelProperty("撤销时间")
  @ApiModelProperty(value = "撤销时间")
  java.util.Date cancelTime;

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
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @ColumnWidth(15)
  @ExcelProperty("更新时间")
  @ApiModelProperty(value = "更新时间")
  java.util.Date updateTime;

  @ExcelIgnore
  @TableField(exist = false)
  java.lang.String noticeUserId;

  @ExcelIgnore
  @TableField(exist = false)
  java.lang.String userId;

  @ExcelIgnore
  @TableField(exist = false)
  private Integer readFlag;

  @ExcelIgnore
  @TableField(exist = false)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  java.util.Date readTime;
}
