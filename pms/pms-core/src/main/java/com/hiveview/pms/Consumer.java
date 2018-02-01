package com.hiveview.pms;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by leo on 2017/12/13.
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("consumer1");

        //同样也要设置NameServer地址
        consumer.setNamesrvAddr("172.16.5.50:9876");
        /**
         * 设置consumer消费策略
         * CONSUME_FROM_LAST_OFFSET   默认消费策略,从该队列队尾开始消费, 即 跳过历史消息
         * CONSUME_FROM_FIRST_OFFSET  从该队列最开始开始 消费,即历史消息(还存在broker的)全部消费一遍
         * CONSUME_FROM_TIMESTAMP     从某个时间点开始消费,和setConsumeTimestamp()配合使用,默认半小时以前.
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        /**
         * 设置consumer订阅的Topic和Tag, "*"代表全部tag
         */
        consumer.subscribe("TopicTest","*");

        /**
         * 设置一个Listener,主要进行消息的逻辑处理
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println(Thread.currentThread().getName()+" Receive new messages "+msg);

                /**
                 * CONSUME_SUCCESS  消费成功
                 * RECONSUME_LATER  消费失败,需要稍后重新消费
                 */
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer started...");
    }
}
