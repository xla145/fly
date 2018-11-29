package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.extend.MemberDetail;
import com.xula.service.member.IWebMemberService;

import java.util.Map;

/**
 * 返回用户信息
 * @author xla
 */
public class GetUserInfo extends ModuleData {


    private IWebMemberService iMemberService = SpringFactory.getBean("IWebMemberService");

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        Object uid = params.get("uid");
        if (uid == null) {
            return root;
        }
        MemberDetail member = iMemberService.getMemberDetail((Integer) uid);
        root.put("data", member);
        return root;
    }
}
