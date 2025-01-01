package com.github.mustsd.modules.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.article.entity.SysArticle;
import com.github.mustsd.modules.article.vo.ArticleTreeNode;

import java.util.List;

/**
 * @author mustsd
 * @date 2022-03-22 17:37:01
 */
public interface ISysArticleService extends IService<SysArticle> {

  List<ArticleTreeNode> loadArticleTree(Boolean personal);
}
