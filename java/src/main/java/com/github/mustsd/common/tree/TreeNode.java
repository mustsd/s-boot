package com.github.mustsd.common.tree;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangz
 * @date 2022-03-12 20:39
 */
@Data
@Accessors(chain = true)
public class TreeNode {

  /** id、key、value 值 */
  private String id;

  private String key;
  private String value;
  /** title 显示 */
  private String title;

  private Object slots;

  private String parentKey;
  private boolean disableCheckbox = false;
  private List<TreeNode> children;

  public TreeNode() {}

  public TreeNode(String id, String key, String value, String title, String parentKey) {
    this.id = id;
    this.key = key;
    this.value = value;
    this.title = title;
    this.parentKey = parentKey;
  }

  public void addChild(TreeNode child) {
    if (children == null) {
      children = new ArrayList<>();
    }
    children.add(child);
  }
}
