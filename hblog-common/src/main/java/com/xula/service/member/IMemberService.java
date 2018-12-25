package com.xula.service.member;

import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.entity.MemberQq;
import com.xula.entity.MemberWb;

import java.util.List;

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



    /**
     * 微博授权登录，注册用户
     * @param nickname
     * @param uuid
     * @return
     */
    RecordBean<Member> registered(String nickname,String uuid,String city,String avatar,String sex,String ip,Integer loginWay);


    /**
     * qq授权登录，注册用户
     * @param openId
     * @param nickname
     * @param uuid
     * @param city
     * @param avatar
     * @param sex
     * @param ip
     * @param loginWay
     * @return
     */
    RecordBean<Member> registered(String openId,String nickname,String uuid,String city,String avatar,String sex,String ip,Integer loginWay);


    /**
     * 创建微博授权信息
     * @param memberWb
     * @return
     */
    RecordBean<MemberWb> createMemberWb(MemberWb memberWb);


    /**
     * 创建QQ授权信息
     * @param memberQq
     * @return
     */
    RecordBean<MemberQq> createMemberQq(MemberQq memberQq);

    /**
     * 更新微博授权信息。当用户解绑的时候，修改状态
     * @param memberWb
     * @return
     */
    RecordBean<MemberWb> updateMemberWb(MemberWb memberWb);


    /**
     * 获取用户信息
     * @param uuid
     * @param loginWay
     * @return
     */
    Member getMemberByUuid(String uuid, Integer loginWay);


    /**
     * 判断用户签到状态
     * @param uid
     * @return
     */
    RecordBean<RecordPojo> checkSignStatus(Integer uid);



}
