package com.xula.service.dict.cache;


import com.xula.base.helper.SpringFactory;
import com.xula.service.dict.vo.Item;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于字典数据缓存处理
 * @author xla
 */
public class DictCache {
	
	private static Logger logger = LoggerFactory.getLogger(DictCache.class);

	private static EhCacheManager cacheManager = SpringFactory.getBean("shiroEhcacheManager");


	/**
	 * 缓存Item数据
	 * 
	 * @param code
	 * @param name
	 * 
	 * @return
	 */
	public static void put(String code, String name, Item item){
		try {
			if(item != null){
				Cache<String,Item> cache = cacheManager.getCache(getCacheName());
				cache.put(getKey(code,name),item);
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
	public static Item get(String code, String name){
		try {
			Cache<String,Item> cache = cacheManager.getCache(getCacheName());
			if (cache != null) {
				return cache.get(getKey(code,name));
			}
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
	public static void invalidate(String code, String name){
		try {
			Cache<String,Item> cache = cacheManager.getCache(getCacheName());
			cache.remove(getKey(code,name));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	

	/**
	 * 删除缓存数据(更新item数据时调用)
	 * 
	 * @return
	 */
	public static void invalidateAll(){
		try {
			Cache<String,Item> cache = cacheManager.getCache(getCacheName());
			cache.clear();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	
	private static String getCacheName(){
		return "dict-cache";
	}

	private static String getKey(String code, String name){
		return "com.xula.service.dict.cache." + code + "." + name;
	}
}
