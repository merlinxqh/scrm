package com.hiveview.schedule.job;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.hiveview.schedule.entity.ScheduleJob;
import com.hiveview.schedule.enums.ScheduleTypeEnum;

/**
 * JobDubboServiceUtils
 * 
 * @author CMM
 *
 * 2016年6月1日 下午6:11:36
 */
public class JobDubboServiceUtils {

	/**
	 * 把job转换成可以被泛化调用的服务对象，并把这些服务存放到Map中。
	 * 在job执行时，再从Map中获取出服务，再以泛化的方式调用dubbo的服务。
	 * @param job 调度任务对象
	 */
	public static void setService(ScheduleJob job){
		if(job.getScheduleType().equals(ScheduleTypeEnum.RPC)){
			ReferenceBean<GenericService> referenceConfig=getReferenceConfig(job);
			GenericService myService = (GenericService) SingletonDubboApplication.getInstance().getReferenceObject(referenceConfig.getId());

			JobDubboGenericServiceMap.getInstance().serviceMap.put(referenceConfig.getId(), myService);
		}
	}


	/**
	 * 从存放 泛化调用服务对象 map中清除 一个job
	 * @param job
	 */
	public static void removeService(ScheduleJob job){
		if(job.getScheduleType().equals(ScheduleTypeEnum.RPC)){
			ReferenceBean<GenericService> referenceConfig=getReferenceConfig(job);
			JobDubboGenericServiceMap.getInstance().serviceMap.remove(referenceConfig.getId());
		}
	}


	public static ReferenceBean<GenericService> getReferenceConfig(ScheduleJob job){
		ReferenceBean<GenericService> referenceConfig = new ReferenceBean<>();
		referenceConfig.setInterface(job.getBeanClass());
		referenceConfig.setGeneric(Boolean.TRUE);
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setId(job.getBeanClass().concat(".").concat(job.getMethodName()));
		SingletonDubboApplication.getInstance().destroyBean(referenceConfig.getId());
		SingletonDubboApplication.getInstance().addReferenceBean(referenceConfig.getId(), referenceConfig);
		return referenceConfig;
	}





}
