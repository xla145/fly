package com.xula.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.SpringFactory;
import com.xula.base.utils.WebReqUtils;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.entity.extend.MemberDetail;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器，主要是为了传递session存储的user到页面
 * @author xla
 */
public class UserInterceptor implements HandlerInterceptor {


    private IWebMemberService iMemberService = SpringFactory.getBean("IWebMemberService");


    long start = System.currentTimeMillis();
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        int uid = WebReqUtils.getSessionUid(request);
        MemberDetail member = iMemberService.getMemberDetail(uid);
        if (modelAndView == null) {
            return;
        }
        if (member == null) {
            member = new MemberDetail();
        }
        modelAndView.addObject("member",member);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
