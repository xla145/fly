package com.xula.service.member;

import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;

/**
 * 用户服务层
 * @author xla
 */
public interface IMemberService {


    /**
     * 注册
     * @param nickname
     * @param password
     * @param email
     * @return
     */
    RecordBean<Member> registered(String nickname,String password,String email);


    /**
     * 注册
     * @param password
     * @param email
     * @return
     */
    RecordBean<Member> login(String password,String email);


}
