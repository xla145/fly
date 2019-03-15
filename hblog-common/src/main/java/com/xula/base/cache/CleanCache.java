package com.xula.base.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis cache annotations
 *
 * @author caibin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CleanCache {


    String[] key() default {};

    /**
     * prefix of key
     * if key is created by auto in system , prefix of key is class name + method name
     * @return
     */
    String prefix() default "";

}
