package com.github.mustsd.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.system.service.ISysUserOrgService;
import com.github.mustsd.modules.system.entity.SysUserOrg;
import com.github.mustsd.modules.system.mapper.SysUserOrgMapper;
import com.github.mustsd.modules.system.vo.SysUserOrgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author manage
 * @date 2022-06-07 15:03:51
 */
@Service
public class SysUserOrgServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg>
        implements ISysUserOrgService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserOrg(SysUserOrgVO userOrgVO) {
        String userId = userOrgVO.getUserId();
        List<String> ordIds = userOrgVO.getOrgIds();
        List<SysUserOrg> userOrgList =
                this.list(new LambdaQueryWrapper<SysUserOrg>().eq(SysUserOrg::getUserId, userId));
        List<String> deleteIds =
                userOrgList.stream()
                        .map(x -> x.getOrgId())
                        .filter(
                                orgId -> {
                                    boolean flag = ordIds.contains(orgId);
                                    if (flag) {
                                        ordIds.remove(orgId);
                                    }
                                    return !flag;
                                })
                        .collect(Collectors.toList());
        List<SysUserOrg> newList =
                ordIds.stream()
                        .map(
                                orgId -> {
                                    SysUserOrg userOrg = new SysUserOrg();
                                    userOrg.setUserId(userId);
                                    userOrg.setOrgId(orgId);
                                    return userOrg;
                                })
                        .collect(Collectors.toList());
        if (!(deleteIds.size() == 0
                || this.getBaseMapper()
                .delete(
                        new LambdaQueryWrapper<SysUserOrg>()
                                .eq(SysUserOrg::getUserId, userId)
                                .in(SysUserOrg::getOrgId, deleteIds))
                > 0)) {
            throw new RuntimeException("删除机构失败");
        }
        if (!(ordIds.size() == 0 || this.saveBatch(newList))) {
            throw new RuntimeException("新增机构失败");
        }
    }
}
