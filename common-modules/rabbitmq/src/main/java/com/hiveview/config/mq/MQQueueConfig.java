package com.hiveview.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 2018/1/30.
 * 队列绑定交换机 示例
 */
@Configuration
public class MQQueueConfig {

    @Autowired
    private TopicExchange topicExchange;

    @Autowired
    private FanoutExchange fanoutExchange;

    @Autowired
    private DirectExchange directExchange;

    public static final String TEST_QUEUE_1="test.queue.1";

    public static final String TEST_QUEUE_2="test.queue.2";

    public static final String TEST_QUEUE_3="test.queue.3";

    @Bean
    public Queue testQueue1(){
        return new Queue(TEST_QUEUE_1);
    }

    @Bean
    public Queue testQueue2(){
        return new Queue(TEST_QUEUE_2);
    }

    @Bean
    public Queue testQueue3(){
        return new Queue(TEST_QUEUE_3);
    }

    @Bean
    public Binding binding1(){
       Binding binding = BindingBuilder.bind(testQueue1()).to(topicExchange).with("*.queue.*");
       return binding;
    }

    @Bean
    public Binding binding2(){
        Binding binding = BindingBuilder.bind(testQueue2()).to(fanoutExchange);
        return binding;
    }

    @Bean
    public Binding binding3(){
        Binding binding = BindingBuilder.bind(testQueue3()).to(directExchange).with(TEST_QUEUE_3);
        return binding;
    }

}
