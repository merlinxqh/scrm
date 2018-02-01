package com.hiveview.config.mq;

import com.hiveview.rabbitmq.MqMsgDemo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by leo on 2018/1/30.
 * MQ消息监听示例
 */
@Configuration
public class MQListenerConfig {


    @RabbitListener(queues= MQQueueConfig.TEST_QUEUE_1, containerFactory="rabbitListenerContainerFactory")
    public void listener1(@Payload MqMsgDemo foo){
        System.out.println("queue1 receive msg "+foo);
    }

    @RabbitListener(queues= MQQueueConfig.TEST_QUEUE_2, containerFactory="rabbitListenerContainerFactory")
    public void listener2(@Payload String foo){
        System.out.println("queue2 receive msg "+foo);
    }

    @RabbitListener(queues= MQQueueConfig.TEST_QUEUE_3, containerFactory="rabbitListenerContainerFactory")
    public void listener3(@Payload String foo){
        System.out.println("queue3 receive msg "+foo);
    }
}
