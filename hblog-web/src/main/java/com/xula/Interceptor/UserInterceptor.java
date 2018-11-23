package com.xula.Interceptor;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.xula.base.utils.SpringFactory;
import com.xula.base.utils.WebReqUtils;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器，主要是为了传递session存储的user到页面
 * @author xla
 */
public class UserInterceptor implements HandlerInterceptor {


    private IMemberService iMemberService = SpringFactory.getBean("IMemberService");


    long start = System.currentTimeMillis();
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        int uid = WebReqUtils.getSessionUid(request);
        Member member = new Member();
        if (uid > 0) {
            member = iMemberService.getMember(uid);
        }
        if (modelAndView == null) {
            return;
        }
        modelAndView.addObject("session",member);
        System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
