package com.github.mustsd.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.system.entity.SysUserOrg;
import com.github.mustsd.modules.system.vo.SysUserOrgVO;

/**
 * @author manage
 * @date 2022-06-07 15:03:51
 */
public interface ISysUserOrgService extends IService<SysUserOrg> {

    /**
     * 批量维护用户机构权限
     *
     * @param userOrgVO
     */
    void editUserOrg(SysUserOrgVO userOrgVO);
}
