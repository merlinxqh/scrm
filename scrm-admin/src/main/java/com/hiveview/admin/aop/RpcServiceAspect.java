package com.hiveview.admin.aop;

import com.alibaba.fastjson.JSON;
import com.hiveview.base.common.rpc.RpcAopModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 2017/11/8.
 */
@Component
@Aspect
public class RpcServiceAspect {
    private static final Logger log= LoggerFactory.getLogger(RpcServiceAspect.class);

    /**
     * 切入点
     */
    @Pointcut("execution(*  com.hiveview.*.api..*(..))")
    public void aspect(){

    }

//    /**
//     * 前置通知
//     */
//    @Before("aspect()")
//    public void before(JoinPoint joinPoint){
//        log.info("前置通知开始.........");
//        printPoint(joinPoint);
//        log.info("前置通知结束.........");
//    }
//
//    /**
//     * 后置通知
//     * @param joinPoint
//     */
//    @After("aspect()")
//    public void after(JoinPoint joinPoint){
//        log.info("后置通知开始.........");
//        printPoint(joinPoint);
//        log.info("后置通知结束.........");
//    }
//
//    /**
//     * 环绕通知
//     * @param joinPoint
//     */
////    @Around("aspect()")
//    public void around(JoinPoint joinPoint){
//
//        log.info("环绕通知开始.........");
//        printPoint(joinPoint);
//        log.info("环绕通知结束.........");
//        try {
//            ((ProceedingJoinPoint)joinPoint).proceed();
//        }catch (Throwable e){
//            log.info("",e.getMessage());
//        }
//    }
//
//    /**
//     * 后置返回通知
//     */
//    @AfterReturning("aspect()")
//    public void afterReturn(JoinPoint joinPoint){
//        log.info("后置返回通知开始.........");
//        printPoint(joinPoint);
//        log.info("后置返回通知结束.........");
//    }

    /**
     * 配置抛出异常后通知
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "aspect()",throwing = "ex")
    public void afterThrow(JoinPoint joinPoint,Exception ex){
        log.info("抛出异常后通知开始.........");
        printPoint(joinPoint);
        log.info("抛出异常后通知结束.........");
        log.info("afterThrow " + joinPoint + "\t" + ex.getMessage()+ ex.getClass());
    }

    public void printPoint(JoinPoint joinPoint){
       log.info("目标方法名称: "+joinPoint.getSignature().getName());
       log.info("目标方法所属类的简单类名: "+joinPoint.getSignature().getDeclaringType().getSimpleName());
       log.info("目标方法所属类的类名:"+joinPoint.getSignature().getDeclaringTypeName());
       log.info("参数列表:"+JSON.toJSONString(joinPoint.getArgs()));
       for(Object obj:joinPoint.getArgs()){
           System.out.println(obj.getClass());
       }
       log.info("目标对象:"+joinPoint.getTarget());
       log.info("代理对象:"+joinPoint.getThis());
       RpcAopModel data=new RpcAopModel(joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName(),joinPoint.getArgs());
//     TODO 将data发送至消息队列
    }




}
