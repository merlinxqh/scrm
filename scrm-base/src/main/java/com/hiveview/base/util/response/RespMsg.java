package com.hiveview.base.util.response;

/**
 * Created by leo on 2017/10/20.
 * 通用返回结果封装
 */
public class RespMsg<T> {

    private long code;

    private String msg;

    private T data;

    public static <T> RespMsg<T> successResp(String msg,T data){
        return new RespMsg<>(RespCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> RespMsg<T> failResp(String msg,T data){
        return new RespMsg<>(RespCode.FAIL.getCode(),msg,data);
    }

    public static <T> RespMsg<T> successResp(String msg){
        return successResp(msg,null);
    }

    public static <T> RespMsg<T> failResp(String msg){
        return failResp(msg,null);
    }

    public static <T> RespMsg<T> successResp(T data){
        return successResp(RespCode.SUCCESS.getName(),data);
    }

    public static <T> RespMsg<T> failResp(T data){
        return failResp(RespCode.FAIL.getName(),data);
    }

    public static <T> RespMsg<T> successResp(){
        return successResp(RespCode.SUCCESS.getName());
    }

    public static <T> RespMsg<T> failResp(){
        return failResp(RespCode.FAIL.getName());
    }

    public RespMsg(long code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public RespMsg(long code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
