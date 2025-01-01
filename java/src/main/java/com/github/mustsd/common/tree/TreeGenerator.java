package com.github.mustsd.common.tree;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yangz
 * @date 2022-03-12 20:39
 */
public class TreeGenerator<T> {

  public List<TreeNode> generateTree(
      List<T> list,
      String parentKey,
      Function<T, ?> keyField,
      Function<T, ?> valueField,
      Function<T, ?> parentField) {
    List<TreeNode> treeNodes =
        list.parallelStream()
            .map(
                t -> {
                  Object key = keyField.apply(t);
                  Object value = valueField.apply(t);
                  Object parent = parentField.apply(t);
                  return new TreeNode(
                      key == null ? "" : key.toString(),
                      key == null ? "" : key.toString(),
                      key == null ? "" : key.toString(),
                      value == null ? "" : value.toString(),
                      parent == null ? "" : parent.toString());
                })
            .collect(Collectors.toList());
    return genTree(treeNodes, new TreeNode().setKey(parentKey));
  }

  private List<TreeNode> genTree(List<TreeNode> treeNodes, TreeNode parent) {
    for (TreeNode perm : treeNodes) {
      if (parent.getKey().equals(perm.getParentKey())) {
        parent.addChild(perm);
        genTree(treeNodes, perm);
      }
    }
    return parent.getChildren();
  }
}
