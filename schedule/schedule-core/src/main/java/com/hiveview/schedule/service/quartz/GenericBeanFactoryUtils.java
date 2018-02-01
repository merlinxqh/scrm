package com.hiveview.schedule.service.quartz;


import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * GenericBeanFactoryUtils
 * 
 * @author CMM
 *
 * 2016年3月29日 下午3:54:16
 */
@Component
public class GenericBeanFactoryUtils implements BeanFactoryPostProcessor {
	
	private static ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Assert.notNull(beanFactory, "'beanFactory' is required");
		GenericBeanFactoryUtils.beanFactory = beanFactory;
	}
	
	public static final Object getBean(String name) throws BeansException {
		return beanFactory.getBean(name);
	}

	public static final <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return beanFactory.getBean(name, requiredType);
	}

	public static final <T> T getBean(Class<T> requiredType) throws BeansException {
		return beanFactory.getBean(requiredType);
	}

	public static final Object getBean(String name, Object... args) throws BeansException {
		return beanFactory.getBean(name, args);
	}

	public static final boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	public static final boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	public static final boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isPrototype(name);
	}

	public static final boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
		return beanFactory.isTypeMatch(name, targetType);
	}

	public static final Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	public static final String[] getAliases(String name) {
		return beanFactory.getAliases(name);
	}

	/**
	 * 按 Bean 类型筛选 Bean
	 * 
	 * @param beanType
	 * @return Map<String, T>
	 */
	public static final <T> Map<String, T> getBeansOfType(Class<T> beanType) {
		return beanFactory.getBeansOfType(beanType, true, true);
	}

	/**
	 * 按 Bean 类型筛选 Bean
	 * 
	 * @param beanType
	 * @param includeNonSingletons
	 * @param allowEagerInit
	 * @return Map<String, T>
	 */
	public static final <T> Map<String, T> getBeansOfType(Class<T> beanType, boolean includeNonSingletons, boolean allowEagerInit) {
		return beanFactory.getBeansOfType(beanType, includeNonSingletons, allowEagerInit);
	}

	/**
	 * 按 Bean 注解筛选 Bean

	 * @param annotationType
	 * @return Map<String, Object>
	 */
	public static final Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return beanFactory.getBeansWithAnnotation(annotationType);
	}

	/**
	 * 获取 Bean 工厂
	 * 
	 * @return ConfigurableListableBeanFactory
	 */
	public static final ConfigurableListableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	/**
	 * 自动注入 Bean 属性(可注入的：setters、@Autowired、@Resource、@Required)
	 * 
	 * @param existingBean
	 * @param autowireMode
	 * @param dependencyCheck
	 */
	public static final void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck) {
		beanFactory.autowireBeanProperties(existingBean, autowireMode, dependencyCheck);
	}

	/**
	 * 求值 SpEL 表达式
	 * 
	 * @param expression
	 * @return Object
	 */
	public static final Object evaluateSpelExpression(String expression) {
		return beanFactory.getBeanExpressionResolver().evaluate(expression, new BeanExpressionContext(beanFactory, null));
	}

	/**
	 * 注册 @Configuration
	 * 
	 * @param annotationConfiguration
	 */
	public static final void registerAnnotationConfiguration(Class<?> annotationConfiguration) {
		BeanDefinitionRegistry registryToUse = (BeanDefinitionRegistry) beanFactory;
		BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(annotationConfiguration).getBeanDefinition();
		String beanName = BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registryToUse);

		if (AnnotationUtils.findAnnotation(annotationConfiguration, Configuration.class) == null) {
			throw new BeanCreationException(beanName, "Bean type [" + annotationConfiguration.getName() + "] is not annotated as @Configuration");
		}

		registryToUse.registerBeanDefinition(beanName, beanDefinition);
		ConfigurationClassPostProcessor ccp = beanFactory.getBean(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, ConfigurationClassPostProcessor.class);
		ccp.processConfigBeanDefinitions(registryToUse);
		ccp.enhanceConfigurationClasses(beanFactory);
	}

	/**
	 * 发布事件
	 * 
	 * @param event
	 */
	public static final void publishEvent(ApplicationEvent event) {
		for (HierarchicalBeanFactory candidate = beanFactory; candidate != null;) {
			candidate.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class).multicastEvent(event);
			BeanFactory parentBeanFactory = candidate.getParentBeanFactory();
			if (HierarchicalBeanFactory.class.isInstance(parentBeanFactory)) {
				candidate = (HierarchicalBeanFactory) parentBeanFactory;
			}
		}
	}

}
