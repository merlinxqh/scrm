package com.hiveview.admin.commom;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leo on 2017/6/13.
 * 异常处理
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private static Logger logger= LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView=new ModelAndView();

        /**
         * 判断请求是 同步 还是 异步(ajax)
         */
        if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            try {
                if(ex instanceof UnauthorizedException){
                    response.setStatus(HttpCodeConstant.SYS_UN_AUTH);
                    response.getWriter().write(HttpCodeConstant.SYS_UN_AUTH);
                    response.getWriter().close();
                }
            }catch (IOException e){
                logger.error("",e);
            }
        }else{//同步请求
            if(ex instanceof UnauthorizedException){
                return new ModelAndView("error/403");
            }
        }

        return modelAndView;
    }
}
