package com.xula.service.member;


import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.entity.MemberQq;
import com.xula.entity.MemberWb;
import com.xula.entity.extend.MemberDetail;
import com.xula.entity.extend.SignList;

import java.util.List;

/**
 * web项目的用户管理接口
 * 主要处理业务逻辑，数据的组装
 * @author xla
 */
public interface IWebMemberService {


    /**
     * 获取用户的详情
     * @param uid
     * @return
     */
    MemberDetail getMemberDetail(int uid);


    /**
     * 统计签到记录
     * @return
     */
    List<List<SignList>> getSignedList();


    /**
     * 更新头像
     * @param avatar
     * @param uid
     * @return
     */
    RecordBean<String> updateAvatar(String avatar,Integer uid);


    /**
     * 更新密码
     * @param nowPwd
     * @param pwd
     * @return
     */
    RecordBean<String> updatePwd(String nowPwd,String pwd,Integer uid);


    /**
     * 获取qq授权信息
     * @param uid
     * @return
     */
    MemberQq getMemberQq(int uid);


    /**
     * 获取微博授权信息
     * @param uid
     * @return
     */
    MemberWb getMemberWb(int uid);


    /**
     * 更新用户信息
     * @param member
     * @return
     */
    RecordBean<Member> updateMember(Member member);

}
