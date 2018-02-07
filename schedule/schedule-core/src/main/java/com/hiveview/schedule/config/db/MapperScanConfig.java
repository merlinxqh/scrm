package com.hiveview.schedule.config.db;

import com.hiveview.base.mybatis.annotation.MyBatisDao;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperScanConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		configurer.setAnnotationClass(MyBatisDao.class);
		configurer.setBasePackage("com.hiveview.schedule.dao");
		return configurer;
	}
}
