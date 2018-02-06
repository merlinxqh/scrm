package com.hiveview.pms.config;

import com.hiveview.base.spring.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by leo on 2018/2/6.
 */
@Configuration
public class PmsConfiguration {

    @Bean
    @Lazy(value = false)
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
