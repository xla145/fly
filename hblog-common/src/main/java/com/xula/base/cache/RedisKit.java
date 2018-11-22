package com.xula.base.cache;


import com.xula.base.constant.BaseConstant;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * redis 操作
 *
 * @author xla
 */
@Repository("redisKit")
public class RedisKit<T> {

    private static Logger logger = LoggerFactory.getLogger(RedisKit.class);

    /**
     * 默认超时时间
     */
    public static final int TIMEOUT = 1500;

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    /**
     * 添加数据缓存到
     *
     * @param key
     * @param exp   超时时间,单位秒
     * @param value
     */
    public void add(String key, int exp, T value) {
        try {
            if (value == null) {
                return;
            }
            if (value instanceof ArrayList && ((ArrayList) value).size() < 1) {
                return;
            }
            if (value instanceof HashMap && ((HashMap) value).size() < 1) {
                return;
            }
            if (!BaseConstant.isDev) {
                redisTemplate.boundValueOps(key).set(value,exp,TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            logger.error("redis operate error: ", e);
        }
    }

    /**
     * 从缓存中读取数据
     *
     * @param key
     * @return
     */
    public T get(String key) {
        T value = null;
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            value = redisTemplate.boundValueOps(key).get();
        } catch (Exception e) {
            logger.error("redis operate error: ", e);
        }
        return value;
    }

    /**
     * 根据key删除数据
     *
     * @param key
     */
    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("redis operate error: ", e);
        }
    }
}
