package com.xula.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年9月18日 上午9:31:36
 */
public interface BaseDao<T> {


	void save(T t);

	/**
	 * 传入map
	 * @param map
	 */
	void save(Map<String, Object> map);

	/**
	 * 选择性添加，字段是空的情况不添加
	 * @param t
	 */
	void saveSelective(T t);


	/**
	 * 返回自增主键
	 * @param t
	 * @return
	 */
	int saveReturnId(T t);

	/**
	 * 批量保存
	 * @param list
	 */
	void saveBatch(List<T> list);


	int update(T t);

	/**
	 * 选择性更新
	 * @param t
	 * @return
	 */
	int updateByPrimaryKeySelective(T t);

	int update(Map<String, Object> map);
	
	int delete(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	T get(Object id);
	
	List<T> queryListByPage(Map<String, Object> map);

	List<T> queryList(Map<String, Object> map);
	
	List<T> queryList(Object id);
	
	int queryTotal(Map<String, Object> map);

	int getCount(Map<String, Object> map);

	int queryTotal();
}
