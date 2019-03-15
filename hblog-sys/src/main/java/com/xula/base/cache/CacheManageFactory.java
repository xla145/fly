package com.xula.base.cache;

import com.xula.base.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * cache manage factory
 * @author xla
 */
public class CacheManageFactory {

    private RedisTemplate<String, Object> redisTemplate;
    private ProceedingJoinPoint pjp;

    private static final String PATTERN_KEY = "\\[([0-9a-zA-Z]*)\\]";

    /**
     * create a cache factory
     * @param redisTemplate
     */
    public CacheManageFactory(RedisTemplate<String, Object> redisTemplate,ProceedingJoinPoint pjp) {
        this.redisTemplate = redisTemplate;
        this.pjp = pjp;
    }

    /**
     * create a cache factory
     * @param redisTemplate
     */
    public CacheManageFactory(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * get object from caches
     * @return
     */
    public Object getObject(int expire,String key) throws Throwable {
        // serviceCache 默认存储一些业务缓存 使用ehcache
        Object object = redisTemplate.boundValueOps(key).get();
        if (object != null ) {
            return object;
        }
        //执行调用
        object = pjp.proceed();
        //add 结果到缓存
        if (object != null && expire > 1 && StringUtils.isNotBlank(key)) {
            redisTemplate.boundValueOps(key).set(object,expire, TimeUnit.SECONDS);
        }
        return object;
    }

    /**
     * remove object from caches
     */
    public void removeCache(String[] keys,String prefix) {
        if (StringUtils.isNotEmpty(prefix)) {
            Set<String> keySet=  redisTemplate.keys(prefix + PATTERN_KEY);
            redisTemplate.delete(keySet);
            return;
        }
        if (keys != null && keys.length > 0) {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /**
     * 生成缓存key
     *
     * @param pjp
     * @return
     */
    public String createKey(ProceedingJoinPoint pjp) {
        StringBuffer crudeKey = new StringBuffer(pjp.getTarget().getClass().getSimpleName() + "_" + pjp.getSignature().getName());
        Object[] params = pjp.getArgs();
        crudeKey.append("[");
        StringBuffer paramsKey = new StringBuffer();
        if (params != null) {
            ByteArrayOutputStream byt = null;
            ObjectOutputStream oos = null;
            try {
                byt = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(byt);
                for (Object obj : params) {
                    oos.writeObject(obj);
                    String str = byt.toString("ISO-8859-1");
                    str = java.net.URLEncoder.encode(str, "UTF-8");
                    paramsKey.append("." + str);
                    oos.reset();
                    byt.reset();
                }
            } catch (Exception e) {
                return null;
            } finally {
                try {
                    oos.close();
                    byt.close();
                } catch (IOException e) {
                }
            }
            crudeKey.append(CommonUtil.md5(paramsKey.toString()));
        }
        crudeKey.append("]");
        //防止　key 太长
        return crudeKey.toString();
    }
}
