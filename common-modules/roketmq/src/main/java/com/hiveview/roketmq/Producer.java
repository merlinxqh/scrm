package com.hiveview.roketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Created by leo on 2017/12/13.
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //声明并初始化一个producer
        //需要一个producer group名字作为构造方法的参数，这里为producer1
        DefaultMQProducer producer=new DefaultMQProducer("producer1");

        //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
        producer.setNamesrvAddr("172.16.5.50:9876");//多个用分号 ; 隔开

        //调用start()方法启动一个producer实例
        producer.start();

        for(int i=0;i<10;i++){
            Message msg=new Message("TopicTest","TagA",("Hello RoketMQ"+i).getBytes());
            //发送消息,这里调用的是 同步的方式, 所以会有返回结果
           SendResult res=producer.send(msg);
            System.out.println("返回结果:"+ JSON.toJSONString(res));
        }

        producer.shutdown();

    }
}
