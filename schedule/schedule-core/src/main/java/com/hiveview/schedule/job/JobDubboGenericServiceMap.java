package com.hiveview.schedule.job;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * 用于存放Dubbo泛化服务对象的容器
 * 使用单例实现
 * 
 * @author CMM
 *
 * 2016年4月15日 下午2:39:44
 */
public class JobDubboGenericServiceMap {
	
	public Map<String, GenericService> serviceMap = new HashMap<String, GenericService>();

	private static JobDubboGenericServiceMap instance = new JobDubboGenericServiceMap();  
	
	private JobDubboGenericServiceMap (){}
	
	public static JobDubboGenericServiceMap getInstance() {  
		return instance;  
	}
	
}
