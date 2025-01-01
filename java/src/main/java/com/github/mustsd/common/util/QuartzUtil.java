package com.github.mustsd.common.util;

import com.github.mustsd.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mustsd
 * @date 2024-12-31
 */
@Slf4j
@Component
public class QuartzUtil {

  /** 调度器 */
  @Autowired private Scheduler scheduler;

  /**
   * Pause a job.
   *
   * @param className
   */
  public void pauseJob(String className) {
    try {
      scheduler.pauseJob(JobKey.jobKey(className));
    } catch (SchedulerException e) {
      throw new BusinessException("Pausing failed");
    }
  }

  /**
   * Delete a job.
   *
   * @param className
   */
  public void deleteJob(String className) {
    try {
      scheduler.pauseTrigger(TriggerKey.triggerKey(className));
      scheduler.unscheduleJob(TriggerKey.triggerKey(className));
      scheduler.deleteJob(JobKey.jobKey(className));
    } catch (Exception e) {
      throw new BusinessException("Deleting failed.");
    }
  }

  /**
   * Start a job.
   *
   * @param className
   * @param parameter
   * @param cronExp
   */
  public void startJob(String className, String parameter, String cronExp) {
    try {
      // 启动调度器
      scheduler.start();
      Class clazz = Class.forName(className);
      // 构建job信息
      JobDetail jobDetail =
          JobBuilder.newJob(clazz)
              .withIdentity(className)
              .usingJobData("parameter", parameter)
              .build();

      // 表达式调度构建器(即任务执行的时间)
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);

      // 按新的cronExpression表达式构建一个新的trigger
      CronTrigger trigger =
          TriggerBuilder.newTrigger().withIdentity(className).withSchedule(scheduleBuilder).build();

      scheduler.scheduleJob(jobDetail, trigger);
    } catch (SchedulerException e) {
      throw new BusinessException("创建定时任务失败 %s", className);
    } catch (Exception e) {
      throw new BusinessException("后台找不到该类名 %s", className);
    }
  }
}
