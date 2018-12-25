package com.xula.service.member;


import cn.assist.easydao.pojo.RecordPojo;
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

}
