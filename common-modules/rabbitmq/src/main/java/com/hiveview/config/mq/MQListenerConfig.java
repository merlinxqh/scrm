package com.hiveview.config.mq;

import com.hiveview.rabbitmq.MqMsgDemo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;

/**
 * Created by leo on 2018/1/30.
 * MQ消息监听示例
 */
@Configuration
public class MQListenerConfig {


    @RabbitListener(queues= MQQueueConfig.TEST_QUEUE_1, containerFactory="rabbitListenerContainerFactory")
    public void listener1(@Payload String foo){
        System.out.println("queue1 receive msg "+foo);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 调用出现异常的话, 该消息 会 持续不断地
         */
//        Assert.isTrue(false, "这里报错了");
        System.out.println("消息被成功消费:"+ foo);
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
