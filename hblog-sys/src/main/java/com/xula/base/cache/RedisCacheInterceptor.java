package com.xula.base.cache;

import com.xula.base.utils.CommonUtil;
import com.xula.config.ShiroConfig;
import com.xula.config.redis.RedisConfig;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 缓存拦截器 （memcache或ehcache）
 * 用于 使用注解的方式添加缓存
 *
 * @author caibin
 */
@Aspect
@Component
@AutoConfigureAfter(RedisConfig.class)
public class RedisCacheInterceptor {

    /**
     * 设置缓存注解切入点
     */
    @Pointcut("execution(* com.xula.service.*.*.*(..)) && @annotation(com.xula.base.cache.MCache)")
    private void theMethod() {
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加缓存
     * @param pjp
     * @param mcache
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(mcache)", argNames = "mcache")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, MCache mcache) throws Throwable {
        CacheManageFactory manageFactory = new CacheManageFactory(redisTemplate,pjp);
        String key = StringUtils.isBlank(mcache.key()) ? manageFactory.createKey(pjp) : mcache.key();
        int expire = mcache.expire();
        return manageFactory.getObject(expire,key);
    }

    /**
     * 清除缓存
     * @param cleanCache
     */
    @After(value = "@annotation(cleanCache)", argNames = "cleanCache")
    public void doBasicProfiling(CleanCache cleanCache){
        String[] keys = cleanCache.key();
        String prefix = cleanCache.prefix();
        CacheManageFactory manageFactory = new CacheManageFactory(redisTemplate);
        manageFactory.removeCache(keys,prefix);
    }
}  
