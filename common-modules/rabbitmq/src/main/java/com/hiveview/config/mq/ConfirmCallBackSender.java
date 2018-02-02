package com.hiveview.config.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by leo on 2018/2/2.
 * 消息 消费 确认回调 示例
 */
@Component
public class ConfirmCallBackSender implements RabbitTemplate.ConfirmCallback{

    private static final Logger logger = LoggerFactory.getLogger(ConfirmCallBackSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public ConfirmCallBackSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send msg id:{}",correlationData.getId());
        this.rabbitTemplate.convertAndSend(MQExchangeConfig.TOPIC_EXCHANGE, MQQueueConfig.TEST_QUEUE_1, msg, correlationData);
    }

    /**
     * 消息 确认消费 回调函数
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("confirm id:{}",correlationData.getId()+"           ack:"+ack);
        if(ack){
            System.out.println("消息回调成功");
        }else{
            System.out.println("消息回调失败:"+cause);
        }
    }
}
