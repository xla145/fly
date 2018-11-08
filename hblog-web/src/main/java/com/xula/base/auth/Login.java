package com.xula.base.auth;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤登录态
 * 
 * @author xla
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD, ElementType.TYPE})  
public @interface Login {
	/**
	 * 是否立刻授权登录
	 */
	boolean immed() default false; 
}