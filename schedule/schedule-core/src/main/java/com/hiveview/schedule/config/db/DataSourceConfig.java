package com.hiveview.schedule.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.hiveview.base.db.DynamicRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 2018/1/30.
 * 数据库配置
 */
@Configuration
//@EnableAspectJAutoProxy
//@EnableTransactionManagement(proxyTargetClass = true)  //开启事物注解支持
public class DataSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${jdbc.driverClassName}")
    private String driverClass;

    @Value("${jdbc.master.url}")
    private String masterUrl;

    @Value("${jdbc.slave.url}")
    private String slaveUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${druid.initialSize}")
    private int initPoolSize;

    @Value("${druid.maxActive}")
    private int maxActive;

    @Value("${druid.minIdle}")
    private int minIdle;

    @Value("${druid.validationQuery}")
    private String validationQuery;


    public DruidDataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initPoolSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        //配置获取连接等待超时的时间
        dataSource.setMaxWait(60000l);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        //配置监控统计拦截的filters
        try {
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            logger.error("",e);
        }
        return dataSource;
    }

    /**
     * 动态数据源配置
     * @return
     */
    @Bean
    public DynamicRoutingDataSource targetDataSource(){
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        DruidDataSource slaveDataSource = dataSource();
        slaveDataSource.setUrl(slaveUrl);
        dataSource.setDefaultTargetDataSource(slaveDataSource);

        DruidDataSource masterDataSource = dataSource();
        masterDataSource.setUrl(masterUrl);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicRoutingDataSource.MASTER_DATA_SOURCE, masterDataSource);
        dataSource.setTargetDataSources(targetDataSources);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(targetDataSource());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(targetDataSource());
        /**
         * mybatis配置
         */
        Resource resource=new ClassPathResource("context/mybatis-config.xml");
        sessionFactory.setConfigLocation(resource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sessionFactory.setMapperLocations(resolver.getResources("classpath:com/hiveview/schedule/dao/mapper/*Mapper.xml"));
            return sessionFactory.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Bean(name="sqlSessionTemplate")
    @Scope("prototype")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception{
        SqlSessionTemplate template=new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
}
