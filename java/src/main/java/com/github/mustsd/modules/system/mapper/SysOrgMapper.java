package com.github.mustsd.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mustsd.modules.system.entity.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author manage
 * @date 2022-06-07 15:04:07
 */
public interface SysOrgMapper extends BaseMapper<SysOrg> {
    @Select(
            "select so.id,so.name,so.region from sys_org so "
                    + "inner join sys_user_org suo on suo.org_id=so.id "
                    + "where so.del_flag=0 and suo.user_id=#{userId}")
    List<SysOrg> listByUser(@Param("userId") String userId);
}
