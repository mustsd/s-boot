package com.github.mustsd.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.system.entity.SysOrg;

import java.util.List;

/**
 * @author mustsd
 * @date 2022-06-07 15:04:07
 */
public interface ISysOrgService extends IService<SysOrg> {
    Object listTransferByUser(String userId);

    List<SysOrg> orgListByUser(String userId);
}
