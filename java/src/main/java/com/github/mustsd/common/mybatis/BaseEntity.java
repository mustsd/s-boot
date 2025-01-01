package com.github.mustsd.common.mybatis;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
  /** 创建人 */
  @ExcelIgnore
  @ApiModelProperty(value = "创建人")
  @TableField(fill = FieldFill.INSERT)
  private java.lang.String createBy;
  /** 创建时间 */
  @ExcelIgnore
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(value = "创建时间")
  private java.util.Date createTime;
  /** 更新人 */
  @ExcelIgnore
  @ApiModelProperty(value = "更新人")
  @TableField(fill = FieldFill.UPDATE)
  private java.lang.String updateBy;
  /** 更新时间 */
  @ExcelIgnore
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ApiModelProperty(value = "更新时间")
  private java.util.Date updateTime;
}
