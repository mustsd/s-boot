package com.github.mustsd.modules.system.vo;

import lombok.Data;

import java.util.List;

/**
 * @author mustsd
 * @date 2022-03-13 19:31
 */
@Data
public class GrantPerm {
  private String roleId;
  private List<String> permIds;
}
