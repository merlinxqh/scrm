package com.hiveview.admin.commom;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by leo on 2017/11/3.
 */
public class RequestContextHolder {

    public static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();
    public static ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<HttpServletResponse>();
    public static ThreadLocal<Map<String,String>> currentParam=new NamedThreadLocal<>("当前request参数");

    public static HttpServletRequest getRequest(){
        return currentRequest.get();
    }

    public static HttpServletResponse getResponse(){
        return currentResponse.get();
    }

    public static void initRequestMap(){
        Map<String,String> parameters = new HashMap<>();
        Map map = getRequest().getParameterMap();
        Set keys = map.keySet();
        for(Object key : keys){
            String value=getRequest().getParameter(key.toString());
            if(!StringUtils.isEmpty(value)){
                parameters.put(key.toString(), value.trim());
            }
        }
        currentParam.set(parameters);
    }

    public static Map<String,String> getRequestMap(){
        return currentParam.get();
    }

    public static void removeData(){
        currentRequest.remove();
        currentResponse.remove();
        currentParam.remove();
    }
}
