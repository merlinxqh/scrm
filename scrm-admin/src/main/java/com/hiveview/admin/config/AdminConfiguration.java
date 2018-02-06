package com.hiveview.admin.config;

import com.hiveview.admin.commom.ExceptionHandler;
import com.hiveview.base.spring.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by leo on 2018/2/6.
 */
@Configuration
public class AdminConfiguration {


    /**
     * 全局异常拦截处理
     * @return
     */
    @Bean
    public ExceptionHandler exceptionHandler(){
        return new ExceptionHandler();
    }

    @Bean
    @Lazy(value = false)
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
