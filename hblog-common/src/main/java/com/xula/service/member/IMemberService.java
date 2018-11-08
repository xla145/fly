package com.xula.service.member;

import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;

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
    RecordBean<Member> registered(String nickname, String password, String email);


    /**
     * 注册
     * @param password
     * @param email
     * @return
     */
    RecordBean<Member> login(String password, String email);


    /**
     * 获取用户基本信息
     * @param uid
     * @return
     */
    MemberInfo getMemberInfo(int uid);


    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    Member getMember(int uid);


}
