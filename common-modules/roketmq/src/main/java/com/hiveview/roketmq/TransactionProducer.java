package com.hiveview.roketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;

import java.util.Date;

/**
 * Created by leo on 2018/2/5.
 */
public class TransactionProducer {

    /**
     * 生产者 这里用到的是 TransactionMQProducer
     * 这里涉及到两个角色:  1. 本地事务执行器 TransactionExecutorImpl
     *                  2. 服务器回查客户端 Listener TransactionCheckListenerImpl
     *
     *
     *  如果事务消息发送到MQ上之后, 会回调本地事务执行器; 但是此时消息还是prepare 状态, 对消费者还不可见,
     *  需要 本地事务执行器 返回RMQ一个确认消息.
     * @param args
     * @throws MQClientException
     */
    public static void main(String[] args) throws MQClientException {
        TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("transactionProducerGroup");
        producer.setNamesrvAddr("172.16.5.50:9876");
        producer.setTransactionCheckListener(transactionCheckListener);
        producer.start();

        TransactionExecutorImpl executor = new TransactionExecutorImpl();
        try{
            Message msg1 = new Message("TransactionTopic", "Tag", "KEY1", ("Hello RoketMQ 1").getBytes());

            Message msg2 = new Message("TransactionTopic", "Tag", "KEY2", ("Hello RoketMQ 2").getBytes());

            SendResult sendResult = producer.sendMessageInTransaction(msg1, executor, null);
            System.out.println(new Date()+" msg1:"+sendResult);

            SendResult sendResult2 = producer.sendMessageInTransaction(msg2, executor, null);
            System.out.println(new Date()+" msg2:"+sendResult2);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        producer.shutdown();
    }
}
