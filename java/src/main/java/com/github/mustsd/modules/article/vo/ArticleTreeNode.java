package com.github.mustsd.modules.article.vo;

import lombok.Data;

import java.util.List;

/** 文章树节点 */
@Data
public class ArticleTreeNode {

  /** 标题 */
  String title;

  /** 节点key */
  String key;

  /** 父节点 */
  String pKey;

  /** 描述 */
  String description;

  /** 私人文章 */
  Boolean personal;

  /** 子节点 */
  List<ArticleTreeNode> children;
}
