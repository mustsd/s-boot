package com.github.mustsd.modules.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_user")
@EqualsAndHashCode
@ApiModel(value = "sys_user对象", description = "sys_user")
public class SysUser extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键ID */
  @TableId(type = IdType.ASSIGN_UUID)
  @ExcelProperty("主键ID")
  @ApiModelProperty(value = "主键ID")
  private String id;
  /** 机构主键 */
  @Dict(dictTable = "sys_org", dictText = "name", dictCode = "id")
  @ExcelProperty("机构主键ID")
  private String orgId;
  /** 账号 */
  @ColumnWidth(15)
  @ExcelProperty("账号")
  @ApiModelProperty(value = "账号")
  private String account;
  /** 姓名 */
  @ColumnWidth(15)
  @ExcelProperty("姓名")
  @ApiModelProperty(value = "姓名")
  private String name;
  /** 密码 */
  @ColumnWidth(15)
  @ExcelProperty("密码")
  @ApiModelProperty(value = "密码")
  private String pwd;
  /** 加密盐 */
  @ColumnWidth(15)
  @ExcelProperty("加密盐")
  @ApiModelProperty(value = "加密盐")
  private String salt;
  /** 性别 */
  @ColumnWidth(15)
  @ExcelProperty(value = "性别")
  @Dict(dictCode = "gender")
  @ApiModelProperty(value = "性别")
  private Integer gender;
  /** 头像 */
  @ColumnWidth(15)
  @ExcelProperty("头像")
  @ApiModelProperty(value = "头像")
  private String avatar;
  /** 电话 */
  @ColumnWidth(15)
  @ExcelProperty("电话")
  @ApiModelProperty(value = "电话")
  private String phone;
  /** 邮箱 */
  @ColumnWidth(15)
  @ExcelProperty("邮箱")
  @ApiModelProperty(value = "邮箱")
  private String email;
  /** 逻辑删除 */
  @ExcelIgnore
  @ApiModelProperty(value = "逻辑删除")
  private Integer delFlag;
  /** 用户角色 */
  @ExcelIgnore
  @TableField(exist = false)
  private String roleIds;
  /** 登录时间 */
  @ExcelIgnore
  @TableField(exist = false)
  private Date loginTime;
  /** 登录ip */
  @ExcelIgnore
  @TableField(exist = false)
  private String ip;
  /** 重置密码 */
  @ExcelIgnore
  @TableField(exist = false)
  private String newPwd;
}
