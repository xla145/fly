package com.xula.dao.two;

import com.xula.entity.User;
import org.springframework.stereotype.Repository;

@Repository("UserMapper")
public interface UserMapper {

    /**
     * 删除数据
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(User record);



    /**
     * 插入数据
     * @param record
     * @return
     */
    int insertSelective(User record);


    /**
     * 查询数据
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(User record);

    /**
     * 更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);
}