package com.hiveview.base.common.rpc;

import java.io.Serializable;

/**
 * Created by leo on 2017/11/9.
 * 参数模型
 */
public class RpcArgsModel implements Serializable{

    private Integer index;//参数顺序

    private String jsonStr;//参数 实例 json序列化后内容

    private String className;//参数对应类名

    public RpcArgsModel(Integer index,String jsonStr,String className){
        this.index=index;
        this.jsonStr=jsonStr;
        this.className=className;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
