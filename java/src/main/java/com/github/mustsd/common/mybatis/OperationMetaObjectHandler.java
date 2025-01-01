package com.github.mustsd.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mustsd.modules.shiro.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class OperationMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "createBy", () -> CurrentUser.getAccountName(), String.class);
    this.strictInsertFill(metaObject, "orgId", () -> CurrentUser.getOrgId(), String.class);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(metaObject, "updateBy", () -> CurrentUser.getAccountName(), String.class);
  }
}
