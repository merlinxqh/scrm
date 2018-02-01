package com.hiveview.schedule.job;

import java.util.Map;

import com.hiveview.schedule.service.quartz.GenericBeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.AbstractInterfaceConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;

/**
 * SingletonDubboApplication
 * 
 * @author CMM
 *
 * 2016年3月29日 下午3:10:08
 */
public class SingletonDubboApplication {
	
	private final static Logger logger = LoggerFactory.getLogger(SingletonDubboApplication.class);
	
	/**
	 * 获取 DUBBO 单例应用程序
	 * 
	 * @return Singleton
	 */
	public static Singleton getInstance() {
		return Singleton.instance;
	}

	/**
	 * 获取 DUBBO 运行时应用程序名称
	 * 
	 * @return String
	 */
	public static String getRuntimeApplicationName() {
		return System.getProperty("dubbo.application.name");
	}

	/**
	 * 单例
	 */
	public static class Singleton implements ApplicationListener<ApplicationEvent> {
		
		private static final Singleton instance = new Singleton();

		/** DUBBO-SPRING 应用程序上下文 */
		private final GenericApplicationContext applicationContext = new GenericApplicationContext();

		{
			ConfigurableListableBeanFactory beanFactory = GenericBeanFactoryUtils.getBeanFactory();
			DefaultListableBeanFactory inMemoryBeanFactory = applicationContext.getDefaultListableBeanFactory();

			// 注册应用程事件监听器
			beanFactory.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class).addApplicationListener(this);

			// 应用配置
			Map<String, ?> beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ApplicationConfig.class, false, false);
			if (CollectionUtils.isEmpty(beanMap)) {
				ApplicationConfig applicationConfig = new ApplicationConfig();
				applicationConfig.setName(SingletonDubboApplication.getRuntimeApplicationName());
				inMemoryBeanFactory.registerSingleton("applicationConfig", applicationConfig);
			} else {
				registerSingletons(inMemoryBeanFactory, beanMap);
			}

			// 注册中心配置
			beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, RegistryConfig.class, false, false);
			if (CollectionUtils.isEmpty(beanMap)) {
				RegistryConfig registryConfig = new RegistryConfig();
				registryConfig.setAddress(System.getProperty("dubbo.registry.address"));
				registryConfig.setProtocol("zookeeper");
				inMemoryBeanFactory.registerSingleton("registryConfig", registryConfig);
			} else {
				registerSingletons(inMemoryBeanFactory, beanMap);
			}

			// 协议配置
			beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ProtocolConfig.class, false, false);
			if (CollectionUtils.isEmpty(beanMap)) {
				ProtocolConfig protocolConfig = new ProtocolConfig();
				protocolConfig.setPort(Integer.valueOf(System.getProperty("dubbo.registry.port"), DubboProtocol.DEFAULT_PORT));
				//protocolConfig.setSerialization(System.getProperty("dubbo.protocol.serialization"));
				inMemoryBeanFactory.registerSingleton("protocolConfig", protocolConfig);
			} else {
				registerSingletons(inMemoryBeanFactory, beanMap);
			}

			// 生产者配置
			beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ProviderConfig.class, false, false);
			if (CollectionUtils.isEmpty(beanMap)) {
				ProviderConfig providerConfig = new ProviderConfig();
				//providerConfig.setDelay(NumberUtils.toInt(System.getProperty(""), 0));
				//providerConfig.setRetries(NumberUtils.toInt(System.getProperty(""), 1));
				//providerConfig.setTimeout(NumberUtils.toInt(System.getProperty(""), 60000));
				//providerConfig.setLoadbalance(System.getProperty(""));
				//providerConfig.setFilter(System.getProperty(""));
				inMemoryBeanFactory.registerSingleton("providerConfig", providerConfig);
			} else {
				registerSingletons(inMemoryBeanFactory, beanMap);
			}

			// 消费者配置
			beanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ConsumerConfig.class, false, false);
			if (CollectionUtils.isEmpty(beanMap)) {
				ConsumerConfig consumerConfig = new ConsumerConfig();
				//consumerConfig.setCheck("true".equals(System.getProperty("")));
				//consumerConfig.setRetries(NumberUtils.toInt(System.getProperty(""), 1));
				//consumerConfig.setTimeout(NumberUtils.toInt(System.getProperty(""), 60000));
				//consumerConfig.setValidation(System.getProperty(""));
				//consumerConfig.setFilter(System.getProperty(""));
				inMemoryBeanFactory.registerSingleton("consumerConfig", consumerConfig);
			} else {
				registerSingletons(inMemoryBeanFactory, beanMap);
			}

			// 刷新通用应用程序上下文对象
			applicationContext.refresh();
		}

		@Override
		public void onApplicationEvent(ApplicationEvent event) {
			if (ContextClosedEvent.class.isInstance(event)) {
				if (logger.isInfoEnabled()) {
					logger.info("Receive spring context closed event at: " + event.getTimestamp());
				}
				applicationContext.close();
			}
		}

		/**
		 * 注册 Bean
		 * 
		 * @param beanFactory
		 * @param beanMap
		 */
		private void registerSingletons(DefaultListableBeanFactory beanFactory, Map<String, ?> beanMap) {
			for (Map.Entry<String, ?> entry : beanMap.entrySet()) {
				beanFactory.registerSingleton(String.format("SDA-%d-%s", beanFactory.getSingletonCount(), entry.getKey()), entry.getValue());
			}
		}

		/**
		 * 销毁
		 * 
		 * @param key
		 */
		public void destroyBean(String key) {
			if (applicationContext.containsBean(key)) {
				applicationContext.getDefaultListableBeanFactory().destroySingleton(key);
			}
		}

		/**
		 * 销毁所有
		 * 
		 */
		public void destroyBeans(Class<? extends AbstractInterfaceConfig> type) {
			for (String beanName : applicationContext.getDefaultListableBeanFactory().getBeanNamesForType(type)) {
				destroyBean(beanName);
			}
		}

		/**
		 * 销毁所有
		 * 
		 */
		public void destroyBeans() {
			applicationContext.getDefaultListableBeanFactory().destroySingletons();
		}

		/**
		 * 添加
		 * 
		 * @param key
		 * @param value
		 */
		public void addReferenceBean(String key, ReferenceBean<?> value) {
			if (applicationContext.containsBean(key)) {
				if (logger.isInfoEnabled()) {
					logger.info("ReferenceBean '{}' already added, ignore...", key);
				}
				return;
			}

			try {
				applicationContext.getDefaultListableBeanFactory().registerSingleton(key, value);
				applicationContext.getDefaultListableBeanFactory().registerDisposableBean(key, value);
				ReferenceBean<?> referenceBean = (ReferenceBean<?>) applicationContext.getDefaultListableBeanFactory().getSingleton(key);
				referenceBean.setApplicationContext(applicationContext);
				referenceBean.afterPropertiesSet();
			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
		}

		/**
		 * 获取
		 * 
		 * @param key
		 * @return
		 */
		public Object getReferenceObject(String key) {
			return applicationContext.containsBean(key) ? ReferenceBean.class.cast(applicationContext.getDefaultListableBeanFactory().getSingleton(key)).get() : null;
		}
	}

}
