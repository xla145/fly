package com.xula.service.member;


import com.xula.entity.extend.MemberDetail;

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






}
