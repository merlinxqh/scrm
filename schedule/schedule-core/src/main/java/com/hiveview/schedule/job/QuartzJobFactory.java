package com.hiveview.schedule.job;

import com.hiveview.schedule.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @Description: 计划任务执行处 无状态
 */
@Service
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
	public final Logger log = LoggerFactory.getLogger(QuartzJobFactory.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);
	}
}