package com.hiveview.base.common.rpc;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/11/9.
 * AOP 拦截rpc调用 模型
 */
public class RpcAopModel implements Serializable{

    private String className;//类名

    private String methodName;//方法名

    private List<RpcArgsModel> args;//参数

    /**
     * 构造函数
     * @param className
     * @param methodName
     * @param args
     */
    public RpcAopModel(String className,String methodName,Object[] args){
        this.className=className;
        this.methodName=methodName;
        List<RpcArgsModel> argsList=new ArrayList<>();
        for(int i=0;i<args.length;i++){
            Object obj=args[i];
            argsList.add(new RpcArgsModel(i, JSON.toJSONString(obj),obj.getClass().toString()));
        }
        this.args=argsList;
    }

    /**
     * 没有参数
     * @param className
     * @param methodName
     */
    public RpcAopModel(String className,String methodName){
        this.className=className;
        this.methodName=methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<RpcArgsModel> getArgs() {
        return args;
    }

    public void setArgs(List<RpcArgsModel> args) {
        this.args = args;
    }
}
