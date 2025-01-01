package com.github.mustsd.modules.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.mustsd.modules.quartz.entity.QuartzJob;

/**
 * @author manage
 * @date 2022-03-25 08:52:07
 */
public interface IQuartzJobService extends IService<QuartzJob> {

  /**
   * 校验定时任务合法性
   *
   * @param clazz
   */
  void verifyJobClazz(String clazz);

  /**
   * 新建任务
   *
   * @param sysQuartzJob
   */
  void addJob(QuartzJob sysQuartzJob);

  /**
   * 暂停任务
   *
   * @param id
   */
  void pauseJob(String id);

  /** @param id */
  void startJob(String id);
  /**
   * 删除任务
   *
   * @param id
   */
  void removeJob(String id);

  /**
   * 编辑任务
   *
   * @param sysQuartzJob
   */
  void editJob(QuartzJob sysQuartzJob);
}
