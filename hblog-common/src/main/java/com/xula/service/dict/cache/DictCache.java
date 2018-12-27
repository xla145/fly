package com.xula.service.dict.cache;

import com.xula.base.cache.MCache;
import com.xula.base.cache.RedisKit;
import com.xula.entity.dict.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用于字典数据缓存处理
 * @author xla
 */
@Component
public class DictCache {
	
	private static Logger logger = LoggerFactory.getLogger(DictCache.class);

    /**
     * 字典服务 采用  redis 进行存储
     *
     * 1：如何在spring boot 使用redis
     * 2：
     */
	@Autowired
	private RedisKit<Item> mCacheKit;


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
				mCacheKit.add(getKey(code,name),-1,item);
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
			return mCacheKit.get(getKey(code,name));
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
			mCacheKit.delete(getKey(code, name));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private static String getKey(String code, String name){
		return "com.xula.service.dict.cache." + code + "." + name;
	}
}
