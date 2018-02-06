package com.hiveview.roketmq;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

import java.util.Date;
import java.util.Random;

/**
 * Created by leo on 2018/2/5.
 * 执行本地事务
 */
public class TransactionExecutorImpl implements LocalTransactionExecuter {

    @Override
    public LocalTransactionState executeLocalTransactionBranch(final Message msg, final Object arg) {
        try {
            if(new Random().nextInt(3) == 2){
                int a = 1/0;
            }
            System.out.println(new Date() + "本地事物执行成功,发送确认消息.");
        }catch (Exception e){
            System.out.println(new Date() + "本地事物执行失败.");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
