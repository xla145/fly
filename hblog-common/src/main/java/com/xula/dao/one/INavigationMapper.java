package com.xula.dao.one;

import com.xula.dao.BaseDao;
import com.xula.entity.Navigation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 导航栏操作
 * @author xla
 */
@Repository("INavigationMapper")
public interface INavigationMapper extends BaseDao<Navigation> {


    /**
     * 分页获取数据
     * @param map
     * @return
     */
    @Override
    List<Navigation> queryListByPage(Map<String,Object> map);

    /**
     * 插入数据 返回主键
     * @param t
     * @return
     */
    @Override
    int saveReturnId(Navigation t);


    /**
     * 获取列表
     * @param map
     * @return
     */
    @Override
    List<Navigation> queryList(Map<String,Object> map);
}