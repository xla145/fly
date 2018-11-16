package com.xula.service.dict.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.dict.Item;
import com.xula.service.dict.IDictService;
import com.xula.service.dict.cache.DictCache;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 字典服务接口
 *
 * @author caixb
 */
@Service("IDictService")
public class DictServiceImpl implements IDictService {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据code 和 name获取String值
	 * @param code
	 * @param name
	 * @return
	 */
	@Override
	public String getStringValue(String code, String name){
		return getStringValue(code, name, null);
	}

	/**
	 * 根据code 和 name获取String值
	 * @param code
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@Override
	public String getStringValue(String code, String name, String defaultValue){
		Item item = getDictItem(code, name);
		if(item == null){
			return defaultValue;
		}
		return item.getValue();
	}

	/**
	 * 根据code 和 name获取String值
	 * @param code
	 * @param name
	 * @param regex 切分正则分隔符
	 * @return
	 */
	@Override
	public String[] getStringValues(String code, String name, String regex){
		String varStr = getStringValue(code, name, null);
		if(StringUtils.isBlank(varStr)){
			return null;
		}
		try {
			return varStr.split(regex);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 根据code 和 name获取int值
	 * @param code
	 * @param name
	 * @return
	 */
	@Override
	public Integer getIntegerValue(String code, String name){
		Item item = getDictItem(code, name);
		if(item == null){
			return null;
		}
		try {
			return Integer.valueOf(item.getValue());
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 根据code 和 name获取Integer值
	 * @param code
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@Override
	public int getIntegerValue(String code, String name, int defaultValue){
		Item item = getDictItem(code, name);
		if(item == null){
			return defaultValue;
		}
		try {return Integer.valueOf(item.getValue()).intValue();

		} catch (Exception e) {
			logger.error("", e);
		}
		return defaultValue;
	}

	/**
	 * 根据code 和 name获取Boolean值
	 * @param code
	 * @param name
	 * @return
	 */
	@Override
	public Boolean getBooleanValue(String code, String name){
		Item item = getDictItem(code, name);
		if(item == null){
			return null;
		}
		try {
			return Boolean.valueOf(item.getValue());
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 根据code 和 name获取boolean值
	 * @param code
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@Override
	public boolean getBooleanValue(String code, String name, boolean defaultValue){
		Item item = getDictItem(code, name);
		if(item == null){
			return defaultValue;
		}
		try {
			return Boolean.valueOf(item.getValue()).booleanValue();
		} catch (Exception e) {
			logger.error("", e);
		}
		return defaultValue;
	}

	/**
	 * 根据code 和 name获取BigDecimal值
	 * @param code
	 * @param name
	 * @return
	 */
	@Override
	public BigDecimal getBigDecimalValue(String code, String name){
		return getBigDecimalValue(code, name, null);
	}

	/**
	 * 根据code 和 name获取BigDecimal值
	 * @param code
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@Override
	public BigDecimal getBigDecimalValue(String code, String name, BigDecimal defaultValue){
		Item item = getDictItem(code, name);
		if(item == null){
			return defaultValue;
		}
		try {
			return new BigDecimal(item.getValue());
		} catch (Exception e) {
			logger.error("", e);
		}
		return defaultValue;
	}

	/**
	 * 根据code获取字典
	 * @param code
	 * @return
	 */
	private Item getDictItem(String code, String name){
		if(StringUtils.isBlank(code) || StringUtils.isBlank(name)){
			return null;
		}
		Item item = DictCache.getCache().get(code, name);
		if(item != null){
			return item;
		}

		Conditions conn = new Conditions("group_code", SqlExpr.EQUAL, code);
		conn.add(new Conditions("name", SqlExpr.EQUAL, name), SqlJoin.AND);

		//添加缓存策略
		item = BaseDao.dao.queryForEntity(Item.class, conn);
		if(item != null){
			DictCache.getCache().put(code, name, item);
		}

		return item;
	}

	/**
	 * 根据code 和 name获取 map类型  key:value,key:value,key:value 形式
	 * @param code
	 * @param name
	 * @param regex
	 * @return
	 */
	@Override
	public Map<Integer, String> getMapValue(String code, String name, String regex) {
		if (StringUtils.isEmpty(regex)) {
			regex = ",";
		}
		Item item = getDictItem(code,name);
		if (item == null) {
			return null;
		}
		String value = item.getValue();
		String[] values = value.split(regex);
		Map<Integer,String> map = new HashMap<>(values.length);
		for (String v:values) {
			String[] v1 = v.split(":");
			if (v1.length > 1) {
				map.put(Integer.valueOf(v1[0]),v1[1]);
			}
		}
		return map;
	}
}
