package com.hiveview.base.exception;

import java.util.List;

/**
 * Created by leo on 2017/10/23.
 * rpc调用异常 将 调用参数 & 对应 接口信息 发送到mq
 */
public class RpcException extends Exception {

    private List<Object> parList;//调用参数

    private String apiInfo;//调用接口信息

    private int code;

    public RpcException(String msg){
        super(msg);
    }

    //TODO 待完善
}
