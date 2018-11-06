package com.xula.base.cache;


import com.xula.base.constant.BaseConstant;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;


/**
 * memcacheed 操作
 *
 * @author caibin
 */
public class MCacheKit {

    private static Logger logger = LoggerFactory.getLogger(MCacheKit.class);
    public static final int TIMEOUT = 1500;//操作超时时间，单位 毫秒

    static MemcachedClient memcachedClient;

    public MCacheKit(MemcachedClient memcachedClient) {
        MCacheKit.memcachedClient = memcachedClient;
    }

    /**
     * 添加数据缓存到
     *
     * @param key
     * @param exp   超时时间,单位秒
     * @param value
     */
    @SuppressWarnings("rawtypes")
    public static void add(String key, int exp, Object value) {
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
                memcachedClient.set(key, exp, value, TIMEOUT);
            }
        } catch (Exception e) {
            logger.error("memcache operate error: ", e);
        }
    }

    /**
     * 从缓存中读取数据
     *
     * @param key
     * @return
     */
    public static <T> T get(String key) {
        T value = null;
        if (StringUtils.isBlank(key)) {
            return value;
        }
        try {
            value = memcachedClient.get(key, TIMEOUT);
        } catch (Exception e) {
            logger.error("memcache operate error: ", e);
        }
        return value;
    }

    /**
     * 根据key删除数据
     *
     * @param key
     */
    public static void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        try {
            memcachedClient.delete(key);
        } catch (Exception e) {
            logger.error("memcache operate error: ", e);
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll() {
        try {
            memcachedClient.flushAll();
        } catch (TimeoutException e) {
            logger.error("memcache operate TimeoutException" + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error("memcache operate InterruptedException" + e.getMessage());
            e.printStackTrace();
        } catch (MemcachedException e) {
            logger.error("memcache operate TimeoutException" + e.getMessage());
            e.printStackTrace();
        }
    }
}
