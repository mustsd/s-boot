package com.github.mustsd.modules.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mustsd.modules.notice.entity.SysNotice;
import com.github.mustsd.modules.notice.entity.SysNoticeUser;
import com.github.mustsd.modules.notice.vo.UnreadCount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author manage
 * @date 2022-05-24 09:08:35
 */
public interface SysNoticeUserMapper extends BaseMapper<SysNoticeUser> {

  @Select(
      " select count(*) from sys_notice_user snu "
          + "inner join sys_notice sn on sn.id=snu.notice_id "
          + "where sn.status=2 and snu.read_flag=0 and snu.user_id=#{userId} ")
  long countUnreadNotice(@Param("userId") String userId);

  @Select(
      "select su.account, count(*) unread  from sys_notice_user snu "
          + "inner join sys_notice sn on sn.id=snu.notice_id "
          + "inner join sys_user su on su.id=snu.user_id "
          + "where sn.status=2 and snu.read_flag=0 group by su.account")
  List<UnreadCount> countAllUnread();

  @Select(
      "select sn.*,snu.id as noticeUserId,snu.user_id,snu.read_flag,snu.read_time from sys_notice sn "
          + "inner join sys_notice_user snu on snu.notice_id=sn.id "
          + "where snu.id=#{id}")
  SysNotice getUserNotice(@Param("id") String id);
}
