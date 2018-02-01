package com.hiveview.admin.commom;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by leo on 2017/11/3.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContextHolder.currentRequest.set(request);
        RequestContextHolder.currentResponse.set(response);
        RequestContextHolder.initRequestMap();
        SystemUserUtils.setInitInfo();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContextHolder.removeData();//清除Thread局部变量数据
        SystemUserUtils.clearInitInfo();
    }
}
