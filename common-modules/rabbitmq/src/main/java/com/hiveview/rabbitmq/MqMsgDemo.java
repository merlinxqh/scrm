package com.hiveview.rabbitmq;

import java.io.Serializable;

/**
 * Created by leo on 2018/1/31.
 */
public class MqMsgDemo implements Serializable{
    private String name;

    private String code;

    private Integer age;

    /**
     * 这个构造函数 必须得有
     * 要不然 转不了
     */
    public MqMsgDemo(){

    }

    public MqMsgDemo(String name, String code, Integer age){
        this.name=name;
        this.code=code;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name="+name+",code="+code+",age="+age;
    }
}
