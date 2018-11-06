package com.xula.service.dict.api;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 * 字典服务接口
 *
 * @author caixb
 */
public interface IDictService {

	/**
	 * 根据code 和 name获取String值
	 * @param code
	 * @param name 
	 * @return
	 */
	public String getStringValue(String code, String name);
	
	/**
	 * 根据code 和 name获取String值
	 * @param code
	 * @param name 
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(String code, String name, String defaultValue);
	
	/**
	 * 根据code 和 name获取String值 
	 * @param code
	 * @param name 
	 * @param regex 切分正则分隔符
	 * @return
	 */
	public String[] getStringValues(String code, String name, String regex);
	
	/**
	 * 根据code 和 name获取int值
	 * @param code
	 * @param name 
	 * @return
	 */
	public Integer getIntegerValue(String code, String name);
	
	/**
	 * 根据code 和 name获取Integer值
	 * @param code
	 * @param name 
	 * @param defaultValue
	 * @return
	 */
	public int getIntegerValue(String code, String name, int defaultValue);
	
	/**
	 * 根据code 和 name获取Boolean值
	 * @param code
	 * @param name 
	 * @return
	 */
	public Boolean getBooleanValue(String code, String name);
	
	/**
	 * 根据code 和 name获取boolean值
	 * @param code
	 * @param name 
	 * @param defaultValue
	 * @return
	 */
	public boolean getBooleanValue(String code, String name, boolean defaultValue);
	
	/**
	 * 根据code 和 name获取BigDecimal值
	 * @param code
	 * @param name 
	 * @return
	 */
	public BigDecimal getBigDecimalValue(String code, String name);
	
	/**
	 * 根据code 和 name获取BigDecimal值
	 * @param code
	 * @param name 
	 * @param defaultValue
	 * @return
	 */
	public BigDecimal getBigDecimalValue(String code, String name, BigDecimal defaultValue);


	/**
	 * 根据code 和 name获取 map列表   key:value,key:value,key:value 形式
	 * @param code
	 * @param name
	 * @param regex
	 * @return
	 */
	public Map<Integer,String> getMapValue(String code, String name, String regex);
	
}
