package com.github.mustsd.modules.system.vo;

import lombok.Data;

import java.util.List;

/** 用户机构实体 */
@Data
public class SysUserOrgVO {

    private String userId;
    private List<String> orgIds;
}
