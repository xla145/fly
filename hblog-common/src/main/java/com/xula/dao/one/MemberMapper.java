package com.xula.dao.one;

import com.xula.entity.Member;
import org.springframework.stereotype.Repository;

@Repository("MemberMapper")
public interface MemberMapper {

    int deleteByPrimaryKey(Integer uid);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}