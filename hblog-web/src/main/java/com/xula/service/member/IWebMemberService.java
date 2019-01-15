package com.xula.service.member;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.*;
import com.xula.entity.extend.ArticleList;
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


    /**
     * 获取用户收藏的文章
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    PagePojo<MemberArticle> getMemberArticlePage(Conditions conn, Integer pageNo, Integer pageSize);


    /**
     * 获取用户消息
     * @param conn
     * @return
     */
    List<MemberMessage> getMemberMessageList(Conditions conn);


    /**
     * 阅读用户消息
     * @return
     */
     RecordBean<String> readMemberMessage(int uid);


    /**
     * 删除用户的消息
     * @param ids
     * @param uid
     * @param isAll
     * @return
     */
    RecordBean<String> delMemberMessage(Integer ids,Integer uid,boolean isAll);

    /**
     * 发送激活账号邮件
     * @param email
     * @return
     */
    RecordBean<String> sendEmail(String email);


    /**
     * 修改用户状态为已激活状态
     * @param uid
     * @return
     */
    RecordBean<String> activateEmail(Integer uid);

}
