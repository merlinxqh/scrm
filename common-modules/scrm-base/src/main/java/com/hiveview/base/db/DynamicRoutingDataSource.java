package com.hiveview.base.db;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.NamedThreadLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 动态数据源 实现 读写分离
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements BeanPostProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

	public static final String MASTER_DATA_SOURCE = "MASTER";

	public static final String SLAVE_DATA_SOURCE = "SLAVE";

	private static final ThreadLocal<Object> currentLookupKey = new NamedThreadLocal<Object>("Current lookup key");

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicRoutingDataSource.currentLookupKey.get();
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (TransactionInterceptor.class.isInstance(bean)) {
			ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
			proxyFactoryBean.setInterfaces(MethodInterceptor.class);
			proxyFactoryBean.setTarget(bean);
			proxyFactoryBean.addAdvice(new TransactionInterceptorAdvice());
			proxyFactoryBean.setProxyTargetClass(true);
			return proxyFactoryBean.getObject();
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * 事务数据源决策者
	 * 
	 */
	public class TransactionInterceptorAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
//			LOGGER.info("-------------------isSynchronizationActive:{}," +
//							"isActualTransactionActive:{},isCurrentTransactionReadOnly:{}",
//					TransactionSynchronizationManager.isSynchronizationActive(),
//					TransactionSynchronizationManager.isActualTransactionActive(),
//					TransactionSynchronizationManager.isCurrentTransactionReadOnly());
			try {
				if (!TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
					DynamicRoutingDataSource.currentLookupKey.set(MASTER_DATA_SOURCE);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Current lookup key is '{}'", MASTER_DATA_SOURCE);
					}
				}
				return invocation.proceed();
			} finally {
				DynamicRoutingDataSource.currentLookupKey.remove();
			}
		}
	}
	
	/** 
     *  
     * 设置本线程的dbtype 
     *  
     */
    public static void setDbMaster() {  
    	DynamicRoutingDataSource.currentLookupKey.set(MASTER_DATA_SOURCE); 
    }  
  
    /** 
     * clearDBType 
     *  
     * @Title: clearDBType 
     * @Description: 清理连接类型 
     */  
    public static void clearDBType() {  
    	DynamicRoutingDataSource.currentLookupKey.remove();
    }
}
