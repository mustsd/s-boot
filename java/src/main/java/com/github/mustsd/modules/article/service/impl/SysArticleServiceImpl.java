package com.github.mustsd.modules.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.article.service.ISysArticleService;
import com.github.mustsd.modules.article.entity.SysArticle;
import com.github.mustsd.modules.article.mapper.SysArticleMapper;
import com.github.mustsd.modules.article.vo.ArticleTreeNode;
import com.github.mustsd.modules.shiro.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author manage
 * @date 2022-03-22 17:37:01
 */
@Service
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticle>
    implements ISysArticleService {

  @Override
  public List<ArticleTreeNode> loadArticleTree(Boolean personal) {
    String account = CurrentUser.getAccount();
    LambdaQueryWrapper<SysArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysArticle::getCreateBy, account);
    if (!personal) {
      lambdaQueryWrapper.or().eq(SysArticle::getPersonal, false);
    }
    List<SysArticle> sysArticleList =
        this.list(
            lambdaQueryWrapper.select(
                SysArticle::getId,
                SysArticle::getParentId,
                SysArticle::getTitle,
                SysArticle::getDescription,
                SysArticle::getPersonal));

    Supplier<ArticleTreeNode> nodeSupplier = ArticleTreeNode::new;
    List<ArticleTreeNode> nodeList = new ArrayList<>();
    for (SysArticle sysArticle : sysArticleList) {
      ArticleTreeNode node = nodeSupplier.get();
      node.setKey(sysArticle.getId());
      node.setTitle(sysArticle.getTitle());
      node.setPKey(sysArticle.getParentId());
      node.setDescription(sysArticle.getDescription());
      node.setPersonal(sysArticle.getPersonal());
      nodeList.add(node);
    }
    // 过滤根节点
    List<ArticleTreeNode> tree =
        nodeList.stream()
            .filter(node -> StringUtils.isEmpty(node.getPKey()))
            .collect(Collectors.toList());
    for (ArticleTreeNode parent : tree) {
      handleTree(nodeList, parent);
    }
    return tree;
  }

  /**
   * 构造树
   *
   * @param nodeList
   * @param parent
   */
  private void handleTree(List<ArticleTreeNode> nodeList, ArticleTreeNode parent) {
    List<ArticleTreeNode> children =
        nodeList.stream()
            .filter(node -> parent.getKey().equals(node.getPKey()))
            .peek(node -> handleTree(nodeList, node))
            .collect(Collectors.toList());
    parent.setChildren(children);
    for (ArticleTreeNode child : children) {
      handleTree(nodeList, child);
    }
  }
}
