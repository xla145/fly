package com.xula.service.dict.cache;

import com.xula.entity.dict.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 用于字典数据缓存处理
 * @author xla
 */
public class DictCache {
	
	private static Logger logger = LoggerFactory.getLogger(DictCache.class);


	private static DictCache dictCache = new DictCache();


	public static DictCache getCache() {
		return dictCache;
	}

    /**
     * 字典服务 采用  redis 进行存储
     *
     * 1：如何在spring boot 使用redis
     * 2：
     */
	@Autowired
	private RedisTemplate<String,Item> redisTemplate;


	/**
	 * 缓存Item数据
	 * 
	 * @param code
	 * @param name
	 * 
	 * @return
	 */

	public void put(String code, String name, Item item){
		try {
			if(item != null){
				redisTemplate.boundValueOps(getKey(code,name)).set(item);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 获取缓存中的Item数据
	 * 
	 * @param code
	 * @param name
	 * 
	 * @return
	 */
	public Item get(String code, String name){
		try {
			return redisTemplate.boundValueOps(getKey(code,name)).get();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * 删除缓存数据(更新item数据时调用)
	 * 
	 * @param code
	 * @param name
	 * 
	 * @return
	 */
	public void invalidate(String code, String name){
		try {
			redisTemplate.delete(getKey(code, name));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private static String getKey(String code, String name){
		return "com.xula.service.dict.cache." + code + "." + name;
	}
}
