package com.hbu.toutiao.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Lenovo on 2018/4/5.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger=LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.hbu.toutiao.controller.SettingController.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("方法执行之前");
    }
    @AfterReturning(value="execution(* com.hbu.toutiao.controller.SettingController.*(..))",returning = "result")
    public Object afterReturn(Object result){
        logger.info("执行返回后通知");
      return result+"返回后通知";
    }
    @After("execution(* com.hbu.toutiao.controller.SettingController.*(..))")
    public void afterMethod(){
        logger.info("执行后置通知方法执行之后");
    }

    @Around("poincut()")
    public  Object Around(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("环绕执行之前");
        Object o=pjp.proceed();
        logger.info("环绕执行之后"+o);
        o="环绕通知:"+o;
        return  o;
    }
    @AfterThrowing(value = "execution(* com.hbu.toutiao.controller.SettingController.*(..))",throwing = "ex")
    public void exceptinMethod(Exception ex){
        logger.info("发生了异常"+ex.getMessage());
    }

    //定义一个切入点
    @Pointcut(value="execution(* com.hbu.toutiao.controller.SettingController.around(..))")
    public void poincut(){

    }
}
