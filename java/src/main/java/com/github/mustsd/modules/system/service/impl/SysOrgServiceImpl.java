package com.github.mustsd.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.system.entity.SysOrg;
import com.github.mustsd.modules.system.mapper.SysOrgMapper;
import com.github.mustsd.modules.system.service.ISysOrgService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author manage
 * @date 2022-06-07 15:04:07
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg>
        implements ISysOrgService {

    @Override
    public Object listTransferByUser(String userId) {
        List<SysOrg> orgList = this.list();
        Map map = new HashMap(2);
        List<SysOrg> currentList = orgListByUser(userId);
        map.put("allOrg", orgList);
        map.put("currentOrg", currentList);
        return map;
    }

    /** @Description: 用户机构 @Author: xwq @Date: 2022/6/7 16:29 */
    @Override
    public List<SysOrg> orgListByUser(String userId) {
        return baseMapper.listByUser(userId);
    }

}
