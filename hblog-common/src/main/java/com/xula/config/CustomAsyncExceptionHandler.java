package com.xula.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * 异步操作异常处理
 * @author xla
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
 
    @Override
    public void handleUncaughtException(
            Throwable throwable, Method method, Object... obj) {
  
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
     
}