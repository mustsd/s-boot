package com.github.mustsd.modules.notice.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @author manage
 * @date 2022-05-24 09:08:35
 */
@Data
@TableName("sys_notice_user")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "通知用户表", description = "通知用户表")
public class SysNoticeUser implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 通知id */
  @ColumnWidth(15)
  @ExcelProperty("通知id")
  @ApiModelProperty(value = "通知id")
  java.lang.String noticeId;

  /** 用户id */
  @ColumnWidth(15)
  @ExcelProperty("用户id")
  @ApiModelProperty(value = "用户id")
  java.lang.String userId;

  /** 阅读状态 */
  @ColumnWidth(15)
  @ExcelProperty("阅读状态")
  @ApiModelProperty(value = "阅读状态")
  java.lang.Integer readFlag;

  /** 阅读时间 */
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @ColumnWidth(15)
  @ExcelProperty("阅读时间")
  @ApiModelProperty(value = "阅读时间")
  java.util.Date readTime;

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
