package com.hiveview.roketmq;


import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by leo on 2018/2/5.
 * 未决事务, 服务器回查客户端
 */
public class TransactionCheckListenerImpl implements TransactionCheckListener {
    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {
        System.out.println("server check TrMsg "+messageExt.toString());

        /**
         * 由于RMQ迟迟没有收到消息的 确认消息, 因此主动询问这条prepare消息, 是否正常?
         * 可以查询数据库看这条消息是否已被处理
         */
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
