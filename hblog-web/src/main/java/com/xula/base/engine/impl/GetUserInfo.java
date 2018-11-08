package com.xula.base.engine.impl;

import com.alibaba.fastjson.JSONObject;
import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.base.utils.WebReqUtils;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.service.member.IMemberService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 判断用户是否登录，返回用户信息
 * @author xla
 */
public class GetUserInfo extends ModuleData {


    private IMemberService iMemberService = SpringFactory.getBean("IMemberService");

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

        int uid = WebReqUtils.getSessionUid(getRequest());
        if (uid == -1) {
            root.put("data", null);
            return root;
        }
        JSONObject object = new JSONObject();
        Member member = iMemberService.getMember(uid);
        MemberInfo memberInfo = iMemberService.getMemberInfo(uid);
        object.put("vipName",memberInfo.getVipName());
        object.put("uid",uid);
        object.put("nickname",member.getNickname());
        object.put("avatar",member.getAvatar());
        root.put("data", object);
        return root;
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
