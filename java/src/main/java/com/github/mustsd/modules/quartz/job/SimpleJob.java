package com.github.mustsd.modules.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author yangz
 * @date 2022-03-25 16:35
 */
@Slf4j
public class SimpleJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("SimpleJob...执行成功");
  }
}
