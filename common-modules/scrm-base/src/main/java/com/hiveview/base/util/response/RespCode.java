package com.hiveview.base.util.response;

/**
 * Created by leo on 2017/10/20.
 */
public enum RespCode {
    SUCCESS(0L,"操作成功"),
    FAIL(-1L,"操作失败");

    private RespCode(long code,String name){
        this.code=code;
        this.name=name;
    }

    private long code;

    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
