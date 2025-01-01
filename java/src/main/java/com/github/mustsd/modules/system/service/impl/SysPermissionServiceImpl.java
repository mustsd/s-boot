package com.github.mustsd.modules.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.constant.CommonConstant;
import com.github.mustsd.common.tree.TreeNode;
import com.github.mustsd.modules.system.entity.SysPermission;
import com.github.mustsd.modules.system.entity.SysRole;
import com.github.mustsd.modules.system.mapper.SysPermissionMapper;
import com.github.mustsd.modules.system.service.ISysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** @author yangz */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
    implements ISysPermissionService {

  /** 菜单类型 */
  private static final int MENU_TYPE = 1;
  /** 按钮类型 */
  private static final int BUTTON_TYPE = 2;

  Pattern pattern = Pattern.compile("(/)(?<key>[a-z A-Z]+)$");
  private Supplier<TreeNode> nodeSupplier = TreeNode::new;

  @Override
  public List<SysRole> listRoleByPermission(String permissionId) {
    return baseMapper.listRoleByPermission(permissionId);
  }

  @Override
  public Map<String, Object> listUserAuth(String userId) {
    List<SysPermission> permissions = baseMapper.listUserAuth(userId);
    // 全部按钮
    List<SysPermission> allButtons =
        permissions.stream()
            .filter(p -> p.getMenuType().equals(BUTTON_TYPE))
            .collect(Collectors.toList());
    // 过滤用户持有权限
    List<SysPermission> userPerms =
        permissions.stream()
            .filter(p -> StringUtils.hasText(p.getUserId()))
            .collect(Collectors.toList());
    // 过滤菜单
    List<String> keepAliveKeys = new ArrayList<>();
    List<SysPermission> menus =
        userPerms.stream()
            .filter(p -> p.getMenuType().equals(MENU_TYPE))
            .peek(
                p -> {
                  if (p.getKeepAlive() != null && p.getKeepAlive()) {
                    Matcher matcher = pattern.matcher(p.getComponent());
                    if (matcher.find()) {
                      String key = matcher.group("key");
                      p.setKey(key);
                      keepAliveKeys.add(key);
                    }
                  }
                })
            .collect(Collectors.toList());
    // 过滤按钮
    List<SysPermission> buttons =
        userPerms.stream()
            .filter(p -> p.getMenuType().equals(BUTTON_TYPE))
            .collect(Collectors.toList());
    Map<String, Object> nvaMap = new HashMap<>(4);
    nvaMap.put("menus", menus);
    nvaMap.put("buttons", buttons);
    nvaMap.put("allButtons", allButtons);
    nvaMap.put("keepAliveKeys", keepAliveKeys);
    return nvaMap;
  }

  @Override
  public Map<String, Object> listUserPermissionTree(String userId) {
    Set<String> checkedKey = new HashSet<>();
    Set<String> allPermKey = new HashSet<>();
    List<TreeNode> rolePermissions =
        baseMapper.listUserAuth(userId).stream()
            .map(
                p -> {
                  String key = p.getId();
                  TreeNode node = nodeSupplier.get();
                  node.setKey(key);
                  node.setValue(key);
                  node.setTitle(p.getName());
                  node.setParentKey(p.getParentId());
                  node.setDisableCheckbox(true);
                  JSONObject slots = new JSONObject();
                  slots.put("icon", p.getMenuType().equals(MENU_TYPE) ? "menu" : "action");
                  node.setSlots(slots);
                  allPermKey.add(key);
                  if (StringUtils.hasText(p.getUserId())) {
                    checkedKey.add(key);
                  }
                  return node;
                })
            .collect(Collectors.toList());
    Map<String, Object> result = new HashMap<>();
    result.put("treeData", genTree(rolePermissions, null));
    result.put("checkedKey", checkedKey);
    result.put("allPermKey", allPermKey);
    return result;
  }

  @Override
  public Map<String, Object> listRolePermissionTree(String roleId) {
    Set<String> checkedKey = new HashSet<>();
    Set<String> allPermKey = new HashSet<>();
    List<TreeNode> rolePermissions =
        baseMapper.listRolePermission(roleId).stream()
            .map(
                p -> {
                  String key = p.getId();
                  TreeNode node = nodeSupplier.get();
                  node.setKey(key);
                  node.setValue(key);
                  node.setTitle(p.getName());
                  node.setParentKey(p.getParentId());
                  JSONObject slots = new JSONObject();
                  slots.put("icon", p.getMenuType().equals(MENU_TYPE) ? "menu" : "action");
                  node.setSlots(slots);
                  allPermKey.add(key);
                  if (StringUtils.hasText(p.getRoleId())) {
                    checkedKey.add(key);
                  }
                  return node;
                })
            .collect(Collectors.toList());
    Map<String, Object> result = new HashMap<>();
    result.put("treeData", genTree(rolePermissions, null));
    result.put("checkedKey", checkedKey);
    result.put("allPermKey", allPermKey);
    return result;
  }

  @Override
  public List<TreeNode> listOptions() {
    List<TreeNode> permissions =
        list(
                new LambdaQueryWrapper<SysPermission>()
                    .orderByAsc(SysPermission::getSortNo)
                    .select(
                        SysPermission::getId, SysPermission::getParentId, SysPermission::getName))
            .stream()
            .map(
                p -> {
                  TreeNode node = nodeSupplier.get();
                  node.setId(p.getId());
                  node.setKey(p.getId());
                  node.setValue(p.getId());
                  node.setTitle(p.getName());
                  node.setParentKey(p.getParentId());
                  return node;
                })
            .collect(Collectors.toList());
    List<TreeNode> options = new ArrayList<>();
    options.add(
        new TreeNode()
            .setKey(CommonConstant.DEFAULT)
            .setValue(CommonConstant.DEFAULT)
            .setTitle("根菜单"));
    options.addAll(genTree(permissions, null));
    return options;
  }

  @Override
  public List<SysPermission> listTree() {
    List<SysPermission> permissions =
        list(new LambdaQueryWrapper<SysPermission>().orderByAsc(SysPermission::getSortNo));
    ;
    return genTableTree(permissions, null);
  }

  /**
   * 构造菜单树
   *
   * @param perms
   * @param parent
   * @return
   */
  private List<TreeNode> genTree(List<TreeNode> perms, TreeNode parent) {
    parent = parent == null ? new TreeNode().setKey(CommonConstant.DEFAULT) : parent;
    for (TreeNode perm : perms) {
      if (parent.getKey().equals(perm.getParentKey())) {
        parent.addChild(perm);
        genTree(perms, perm);
      }
    }
    return parent.getChildren();
  }

  private List<SysPermission> genTableTree(List<SysPermission> perms, SysPermission parent) {
    parent = parent == null ? new SysPermission().setId(CommonConstant.DEFAULT) : parent;
    for (SysPermission perm : perms) {
      if (parent.getId().equals(perm.getParentId())) {
        parent.addChild(perm);
        genTableTree(perms, perm);
      }
    }
    return parent.getChildren();
  }
}
