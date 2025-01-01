package com.github.mustsd.modules.notice.vo;

import lombok.Data;

/**
 * 未读统计
 *
 * @author yangz
 * @date 2022-05-25 15:45
 */
@Data
public class UnreadCount {
  private String account;
  private Long unread;
  private String title;
}
