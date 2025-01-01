package com.github.mustsd.modules.system.entity;

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
 * @date 2022-06-07 15:03:51
 */
@Data
@TableName("sys_user_org")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户机构表", description = "用户机构表")
public class SysUserOrg implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ColumnWidth(15)
  @ExcelProperty("主键id")
  @ApiModelProperty(value = "主键id")
  java.lang.String id;

  /** 用户主键 */
  @ColumnWidth(15)
  @ExcelProperty("用户主键")
  @ApiModelProperty(value = "用户主键")
  java.lang.String userId;

  /** 机构主键 */
  @ColumnWidth(15)
  @ExcelProperty("机构主键")
  @ApiModelProperty(value = "机构主键")
  java.lang.String orgId;

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

}
