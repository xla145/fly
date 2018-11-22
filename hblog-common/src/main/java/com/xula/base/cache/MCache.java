package com.xula.base.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * memcache注解
 *
 * @author caibin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MCache {

    String key() default "";

    int expire(); //秒为单位

    // ehcache 缓存的名称
    String value() default "";

    boolean isPersistence() default false; // 是否持久化
}