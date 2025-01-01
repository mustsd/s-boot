package com.github.mustsd.modules.system.entity;

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
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 *
 * @author yangz
 * @date 2022-03-03 7:56
 */
@Data
@TableName("sys_permission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "sys_permission对象", description = "sys_permission")
public class SysPermission extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键id */
  @TableId(type = IdType.ASSIGN_UUID)
  @ApiModelProperty(value = "主键id")
  private String id;
  /** 父id */
  @ApiModelProperty(value = "父id")
  private String parentId;
  /** 菜单名称 */
  @ApiModelProperty(value = "名称")
  private String name;
  /** 菜单路径 */
  @ApiModelProperty(value = "路径")
  private String path;
  /** 组件 */
  @ApiModelProperty(value = "组件")
  private String component;
  /** 菜单类型 */
  @ApiModelProperty(value = "菜单类型")
  private Integer menuType;
  /** 页面缓存 */
  @ApiModelProperty(value = "页面缓存")
  private Boolean keepAlive;
  /** 隐藏菜单 */
  @ApiModelProperty(value = "隐藏菜单")
  private Boolean hidden;
  /** 外部页面 */
  @ApiModelProperty(value = "外部页面")
  private Boolean external;
  /** 菜单排序 */
  @ApiModelProperty(value = "菜单排序")
  private Integer sortNo;
  /** 菜单图标 */
  @ApiModelProperty(value = "菜单图标")
  private String icon;
  /** 按钮权限编码 */
  @ApiModelProperty(value = "按钮权限编码")
  private String btnCode;
  /** 按钮权限策略1显示2禁用 */
  @ApiModelProperty(value = "按钮权限策略1显示2禁用")
  private Integer btnAction;
  /** 按钮启用状态 */
  @ApiModelProperty(value = "按钮启用状态")
  private Boolean btnActive;
  /** 功能描述 */
  @ApiModelProperty(value = "功能描述")
  private String description;

  /** 逻辑删除 */
  @ApiModelProperty(value = "逻辑删除")
  private Boolean delFlag;

  @TableField(exist = false)
  private String key;

  @TableField(exist = false)
  private String value;

  @TableField(exist = false)
  private String title;

  @TableField(exist = false)
  private String roleId;

  @TableField(exist = false)
  private String userId;

  /** 子菜单 */
  @TableField(exist = false)
  private List<SysPermission> children;

  public void addChild(SysPermission child) {
    if (children == null) {
      children = new ArrayList<>();
    }
    children.add(child);
  }
}
