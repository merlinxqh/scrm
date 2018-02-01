package com.hiveview.schedule.job;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.hiveview.base.spring.SpringContextHolder;
import com.hiveview.base.util.http.WrapperHttpUtils;
import com.hiveview.schedule.entity.ScheduleJob;
import com.hiveview.schedule.enums.ScheduleTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskUtils {
	public final static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 *
	 * @param scheduleJob
	 */

	public static void invokMethod(ScheduleJob scheduleJob) {
		logger.debug("======================执行任务开始=============================" + scheduleJob.getGenericServiceId());
		if(ScheduleTypeEnum.RPC.equals(scheduleJob.getScheduleType())){
			dubboService(scheduleJob);
		} else if(ScheduleTypeEnum.HTTP.equals(scheduleJob.getScheduleType())) {
			httpService(scheduleJob);
		}else{
			localService(scheduleJob);
		}
		logger.debug("++++++++++++++++++++++执行任务结束+++++++++++++++++++++++++++++" + scheduleJob.getGenericServiceId());
	}

	/**
	 * http调用
	 * @param job
	 */
	public static void httpService(ScheduleJob job){
		logger.info("Job调用http调度服务开始, http url:" + job.getHttpUrl());
		String resp=WrapperHttpUtils.doGet(job.getHttpUrl());
		logger.info("Job调用http调度服务结束, http url:" + job.getHttpUrl()+",返回结果:"+resp);
	}

	/**
	 * 以泛化的方式调用dubbo服务
	 * @param scheduleJob
	 */
	public static void dubboService(ScheduleJob scheduleJob){
		logger.info("Job调用Dubbo服务开始，id：" + scheduleJob.getGenericServiceId());

		GenericService genericService = JobDubboGenericServiceMap.getInstance().serviceMap.get(scheduleJob.getGenericServiceId());
		if(null == genericService){
			logger.info("没有job任务服务被找到(Dubbo泛化调用服务)，ID：" + scheduleJob.getGenericServiceId());
			return ;
		}

		genericService.$invoke(scheduleJob.getMethodName(), null, null);
		logger.info("Job调用Dubbo服务结束，id：" + scheduleJob.getGenericServiceId());
	}

	/**
	 * 调用本地服务
	 * @param scheduleJob
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void localService(ScheduleJob scheduleJob){
		Object object = null;
		Class clazz = null;

		//springId不为空先按springId查找bean
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringContextHolder.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

		}
		if (object == null) {
			logger.error("任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}

		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (NoSuchMethodException e) {
			logger.error("任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		if (method != null) {
			try {
				method.invoke(object);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
