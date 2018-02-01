package com.hiveview.schedule.enums;

/**
 * Created by leo on 2018/1/9.
 */
public enum ScheduleTypeEnum {

    RPC("RPC","rpc调用"),
    HTTP("HTTP","http调用"),
    LOCAL_INVOKE("LOCAL_INVOKE","本系统调用");

    private String code;

    private String name;

    ScheduleTypeEnum(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
