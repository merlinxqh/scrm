package com.hiveview.schedule.config.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 2018/2/5.
 */
@Configuration
@DubboComponentScan(value = "com.hiveview.schedule.api")
public class ScheduleDubboConfig {
}
