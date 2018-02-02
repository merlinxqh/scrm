//package com.hiveview.pms.controller;
//
//import com.hiveview.base.util.response.RespMsg;
//import com.hiveview.config.mq.ConfirmCallBackSender;
//import com.hiveview.config.mq.MQExchangeConfig;
//import com.hiveview.config.mq.MQQueueConfig;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by leo on 2018/2/2.
// */
//@RestController
//@RequestMapping("/mqTest")
//public class MQTestController {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private ConfirmCallBackSender callBackSender;
//
//    @RequestMapping(value = "sendMsg")
//    public RespMsg<?> sendMsg(String msg, String type){
//        if("1".equals(type)){
//            rabbitTemplate.convertAndSend(MQExchangeConfig.TOPIC_EXCHANGE, MQQueueConfig.TEST_QUEUE_1, msg);
//        }else if("2".equals(type)) {
//            /**
//             * 提供 消息被成功消费 回调函数
//             */
//            callBackSender.send(msg);
//        }
//        return RespMsg.successResp();
//    }
//
//}
