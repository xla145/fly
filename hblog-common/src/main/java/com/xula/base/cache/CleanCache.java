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
public @interface CleanCache {
    String key() default "";

    String value() default "";
    // 是否持久化
    boolean isPersistence() default false;
}
