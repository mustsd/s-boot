package com.github.mustsd.modules.article.entity;

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
 * @date 2022-03-22 17:37:01
 */
@Data
@TableName("sys_article")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "文章", description = "文章")
public class SysArticle implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 父id */
  @ColumnWidth(15)
  @ExcelProperty("父id")
  @ApiModelProperty(value = "父id")
  java.lang.String parentId;

  /** 文章标题 */
  @ColumnWidth(15)
  @ExcelProperty("文章标题")
  @ApiModelProperty(value = "文章标题")
  java.lang.String title;

  /** 文章描述 */
  @ColumnWidth(15)
  @ExcelProperty("文章描述")
  @ApiModelProperty(value = "文章描述")
  java.lang.String description;

  /** 封面图片 */
  @ColumnWidth(15)
  @ExcelProperty("封面图片")
  @ApiModelProperty(value = "封面图片")
  java.lang.String cover;

  /** 文章内容 */
  @ColumnWidth(15)
  @ExcelProperty("文章内容")
  @ApiModelProperty(value = "文章内容")
  java.lang.String content;

  /** 渲染内容 */
  @ColumnWidth(15)
  @ExcelProperty("渲染内容")
  @ApiModelProperty(value = "渲染内容")
  java.lang.String render;

  /** 私有文章 */
  @ColumnWidth(15)
  @ExcelProperty("私有文章")
  @ApiModelProperty(value = "私有文章")
  java.lang.Boolean personal;

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
