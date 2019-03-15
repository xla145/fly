package com.xula.base.cas;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于redis 缓存类
 * @param <K>
 * @param <V>
 * @author xla
 */
public class RedisCache<K, V> implements Cache<K, V> {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private RedisTemplate<String,V> redisManager;
	private String keyPrefix = RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX;
	private int expire;
	private String principalIdFieldName = RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

	/**
	 *
	 * @param redisManager redisManager
	 * @param prefix authorization prefix
	 * @param expire expire
	 * @param principalIdFieldName id field name of principal object
	 */
	public RedisCache(RedisTemplate<String,V> redisManager, String prefix, int expire, String principalIdFieldName) {
		 if (redisManager == null) {
	         throw new IllegalArgumentException("redisManager cannot be null.");
	     }
	     this.redisManager = redisManager;
		 if (prefix != null && !"".equals(prefix)) {
			 this.keyPrefix = prefix;
		 }
         this.expire = expire;
		 if (principalIdFieldName != null) {
			 this.principalIdFieldName = principalIdFieldName;
		 }
	}

    /**
	 * 获取认证和授权信息
	 * @param key key
	 * @return value
	 * @throws CacheException get cache exception
	 */
	@Override
	public V get(K key) throws CacheException {
		logger.debug("get key [" + key + "]");
		if (key == null) {
			return null;
		}
		String key1 = getStringRedisKey(key);
		return redisManager.boundValueOps(key1).get();
	}

	/**
	 * 将认证和授权信息存入缓存
	 * @param key
	 * @param value
	 * @return
	 * @throws CacheException
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		if (key == null) {
			logger.warn("Saving a null key is meaningless, return value directly without call Redis.");
			return value;
		}
		String key1 = getStringRedisKey(key);
		redisManager.boundValueOps(key1).set(value,expire, TimeUnit.SECONDS);
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		logger.debug("remove key [" + key + "]");
        if (key == null) {
            return null;
        }
		String key1 = getStringRedisKey(key);
		V previous = redisManager.boundValueOps(key1).get();
		redisManager.delete(key1);
		return previous;
	}

	/**
	 * 获取
	 * @param key
	 * @return
	 */
	private String getStringRedisKey(K key) {
		String redisKey;
		if (key instanceof PrincipalCollection) {
			redisKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
			redisKey = key.toString();
		}
		return redisKey;
	}

	private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
		Object principalObject = key.getPrimaryPrincipal();
		if (principalObject instanceof String) {
		    return principalObject.toString();
		}
		Method pincipalIdGetter = getPrincipalIdGetter(principalObject);
		return getIdObj(principalObject, pincipalIdGetter);
	}

	private String getIdObj(Object principalObject, Method pincipalIdGetter) {
		String redisKey;
		try {
		    Object idObj = pincipalIdGetter.invoke(principalObject);
		    if (idObj == null) {
		        throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
            }
			redisKey = idObj.toString();
		} catch (IllegalAccessException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		} catch (InvocationTargetException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		}
		return redisKey;
	}

	private Method getPrincipalIdGetter(Object principalObject) {
		Method pincipalIdGetter = null;
		String principalIdMethodName = this.getPrincipalIdMethodName();
		try {
			pincipalIdGetter = principalObject.getClass().getMethod(principalIdMethodName);
		} catch (NoSuchMethodException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
		}
		return pincipalIdGetter;
	}

	private String getPrincipalIdMethodName() {
		if (this.principalIdFieldName == null || "".equals(this.principalIdFieldName)) {
			throw new CacheManagerPrincipalIdNotAssignedException();
		}
		return "get" + this.principalIdFieldName.substring(0, 1).toUpperCase() + this.principalIdFieldName.substring(1);
	}


	/**
	 * 清除keys
	 * @throws CacheException
	 */
	@Override
	public void clear() throws CacheException {
		logger.debug("clear cache");
        Set<String> keys = redisManager.keys(this.keyPrefix + "*");

        if (keys == null || keys.size() == 0) {
            return;
        }
        for (String key: keys) {
            redisManager.delete(key);
        }
	}

	/**
	 * get all authorization key-value quantity
	 * @return key-value size
	 */
	@Override
	public int size() {
		Set<String> keys = redisManager.keys(this.keyPrefix + "*");
		return keys == null? 0:keys.size();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		Set<String> keys = redisManager.keys(this.keyPrefix + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		Set<K> convertedKeys = keys.stream().map( s -> (K)s).collect(Collectors.toSet());
		return convertedKeys;
	}

	@Override
	public Collection<V> values() {
		Set<String> keys = redisManager.keys(this.keyPrefix + "*");
		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptySet();
		}
		List<V> values = keys.stream().map(s -> redisManager.boundValueOps(s).get()).collect(Collectors.toList());
		return Collections.unmodifiableList(values);
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getPrincipalIdFieldName() {
		return principalIdFieldName;
	}

	public void setPrincipalIdFieldName(String principalIdFieldName) {
		this.principalIdFieldName = principalIdFieldName;
	}
}
