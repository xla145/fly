package com.xula.base.cache;

import com.xula.base.utils.Md5Utils;
import com.xula.base.utils.SpringFactory;
import com.xula.config.ShiroConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
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
import org.springframework.stereotype.Component;

/**
 * 缓存拦截器 （memcache或ehcache）
 * 用于 使用注解的方式添加缓存
 *
 * @author caibin
 */
@Aspect
@Component
@Import(RedisKit.class)
@AutoConfigureAfter(ShiroConfig.class)
public class CacheInterceptor {

    private Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);

    private static final String SERVICE_CACHE = "serviceCache-30";

    /**
     * 设置缓存注解切入点
     */
    @Pointcut("execution(* com.xula.service.*.*.*(..)) && @annotation(com.xula.base.cache.MCache)")
    private void theMethod() {
    }

    @Autowired
    private RedisKit redisKit;

    /**
     * 添加缓存
     * @param pjp
     * @param mcache
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(mcache)", argNames = "mcache")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, MCache mcache) throws Throwable {
        EhCacheManager cacheManager = SpringFactory.getBean("ehcacheManager");
        String key = StringUtils.isBlank(mcache.key()) ? createKey(pjp) : mcache.key();
        int expire = mcache.expire();
        boolean isPersistence = mcache.isPersistence();
        // serviceCache 默认存储一些业务缓存 使用ehcache
        if (isPersistence) {
            Object object = redisKit.get(key);
            if (object != null ) {
                return object;
            }
            //执行调用
            object = pjp.proceed();
            //add 结果到缓存
            if (object != null && expire > 1 && StringUtils.isNotBlank(key)) {
                redisKit.add(key, expire, object);
            }
            return object;
        }
        String value = StringUtils.isBlank(mcache.value()) ? SERVICE_CACHE : mcache.value();
        Cache<String,Object> cache = cacheManager.getCache(value);
        if (cache != null && cache.get(key) != null) {
            return cache.get(key);
        }
        //执行调用
        Object object = pjp.proceed();
        //add 结果到缓存
        if (object != null && StringUtils.isNotBlank(key)) {
            cache.put(key,object);
        }
        return object;
    }

    /**
     * 清除缓存
     * @param cleanCache
     */
    @After(value = "@annotation(cleanCache)", argNames = "cleanCache")
    public void doBasicProfiling(CleanCache cleanCache){
        EhCacheManager cacheManager = SpringFactory.getBean("shiroEhcacheManager");
        String key = cleanCache.key();
        if (StringUtils.isBlank(key)) {
            new Throwable("key不能为空！");
        }
        boolean isPersistence = cleanCache.isPersistence();
        if (isPersistence) {
            //缓存去取
            Object object = redisKit.get(key);
            if (object != null) {
                redisKit.delete(key);
            }
            return;
        }
        String value = StringUtils.isBlank(cleanCache.value()) ? SERVICE_CACHE : cleanCache.value();
        Cache<String,Object> cache = cacheManager.getCache(value);
        if (cache != null && cache.get(key) != null) {
            cache.remove(key);
        }
    }

    /**
     * 生成缓存key
     * @param pjp
     * @return
     */
    private String createKey(ProceedingJoinPoint pjp) {
        StringBuffer key = new StringBuffer();
        key.append(pjp.getTarget().getClass().getSimpleName());
        key.append(".");
        key.append(pjp.getSignature().getName());
        key.append("[");
        for (Object obj : pjp.getArgs()) {
            key.append(obj.toString());
        }
        key.append("]");
        //防止　key 太长
        return Md5Utils.md5(key.toString());
    }
}  
