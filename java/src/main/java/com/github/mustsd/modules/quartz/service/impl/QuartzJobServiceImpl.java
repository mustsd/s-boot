package com.github.mustsd.modules.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.common.exception.BusinessException;
import com.github.mustsd.common.util.QuartzUtil;
import com.github.mustsd.modules.quartz.entity.QuartzJob;
import com.github.mustsd.modules.quartz.mapper.QuartzJobMapper;
import com.github.mustsd.modules.quartz.service.IQuartzJobService;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author manage
 * @date 2022-03-25 08:52:07
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob>
    implements IQuartzJobService {

  @Autowired QuartzUtil quartzUtil;

  @Override
  public void verifyJobClazz(String clazz) {
    Class<?> jobClazz;
    try {
      jobClazz = this.getClass().getClassLoader().loadClass(clazz);
    } catch (ClassNotFoundException e) {
      throw new BusinessException("未找到当前类 %s", clazz);
    }
    Class[] interfaces = jobClazz.getInterfaces();
    boolean flag = false;
    for (Class inf : interfaces) {
      if (inf.equals(Job.class)) {
        flag = true;
        break;
      }
    }
    if (!flag) {
      throw new BusinessException("当前类 %s 需要继承Job接口", clazz);
    }
  }

  @Override
  public void editJob(QuartzJob job) {
    quartzUtil.deleteJob(job.getJobClass());
    job.setStatus(2);
    this.updateById(job);
  }

  @Override
  public void removeJob(String id) {
    QuartzJob job = this.getById(id);
    if (null == job) {
      throw new BusinessException("任务不存在");
    }
    quartzUtil.deleteJob(job.getJobClass());
    this.removeById(id);
  }

  @Override
  public void pauseJob(String id) {
    QuartzJob job = this.getById(id);
    if (null == job) {
      throw new BusinessException("任务不存在");
    }
    if (job.getStatus().equals(2)) {
      throw new BusinessException("任务已经停止");
    }
    quartzUtil.pauseJob(job.getJobClass());
    job.setStatus(2);
    this.updateById(job);
  }

  @Override
  public void startJob(String id) {
    QuartzJob job = this.getById(id);
    if (null == job) {
      throw new BusinessException("任务不存在");
    }
    if (job.getStatus().equals(1)) {
      throw new BusinessException("任务已经启动");
    }
    String className = job.getJobClass();
    quartzUtil.deleteJob(className);
    quartzUtil.startJob(className, job.getParameter(), job.getCronExpression());
    this.update(
        new LambdaUpdateWrapper<QuartzJob>().eq(QuartzJob::getId, id).set(QuartzJob::getStatus, 1));
  }

  @Override
  public void addJob(QuartzJob job) {
    verifyJobClazz(job.getJobClass());
    job.setStatus(2);
    this.save(job);
  }
}
