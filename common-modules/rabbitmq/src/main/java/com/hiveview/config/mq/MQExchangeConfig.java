package com.hiveview.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 2018/1/30.
 * 交换机exchange 配置
 *
 * DirectExchange:按照routing key分发到指定队列
 * TopicExchange:多关键字匹配
 * FanoutExchange: 将消息分发到所有的绑定队列，无routing key的概念
 * HeadersExchange ：通过添加属性key-value匹配
 */
@Configuration
public class MQExchangeConfig {

    public static final String TOPIC_EXCHANGE="TOPIC_EXCHANGE";

    public static final String FANOUT_EXCHANGE="FANOUT_EXCHANGE";

    public static final String DIRECT_EXCHANGE="DIRECT_EXCHANGE";

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }
}
