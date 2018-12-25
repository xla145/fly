package com.xula.controller.auth;


import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.LoginWayConstant;
import com.xula.base.utils.SpringFactory;
import com.xula.base.utils.CookieUtil;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.ReqUtils;
import com.xula.controller.BaseController;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.entity.MemberToken;
import com.xula.event.EventModel;
import com.xula.event.LoginEvent;
import com.xula.event.RegisterEvent;
import com.xula.event.TaskEvent;
import com.xula.service.member.IMemberService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录、注册、登出 base方法
 *
 * @author xla
 *
 */
public class BaseAuth extends WebController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(BaseAuth.class);

	@Autowired
	public IMemberService iMemberService;

    @Autowired
    public ApplicationContext ac;

    /**
     *  登录成功处理
     *
     * @param request
     * @param response
     * @param member
     * @param loginWay  登录方式 参见：LoginWayConstant
     */
    public RecordBean gotoLogin(HttpServletRequest request, HttpServletResponse response, ApplicationContext ac, Member member, int loginWay){
        if(member == null){
            return RecordBean.error("登录失败， 请稍后在试");
        }
        String userIp = ReqUtils.getIp(request);
//        //登录
//        MemberToken memberToken = memberService.login(member.getUid(), userIp, loginWay, (LoginWayConstant.getDescribe(loginWay) + member.getMobile()));
//        if(memberToken == null){
//            return RecordBean.error("登录失败， 请稍后在试");
//        }

        int uid = member.getUid();
        //登录成功,存入登录用户UID(暂时使用session)
        request.getSession().setAttribute(GlobalConstant.SESSION_UID, uid);

        //存入cookie 用户token
//        long from = memberToken.getLoginTime().getTime();
//        long to = memberToken.getTenancyTerm().getTime();
//        int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
//        int maxAge = days * 86400;
//        CookieUtil.setCookie(response, GlobalConstant.COOKIE_KEY, memberToken.getToken(), maxAge);


        //记录登录日志
        String ip = ReqUtils.getIp(request);
        String userAgent = request.getHeader("user-agent");
        String referer =  request.getHeader("Referer");
        String fr = request.getParameter("fr");

        logger.info("用户登录成功-->uid:{}, ip: {} ,userAgent:{},referer：{},fr:{}",uid,ip,userAgent,referer,fr);

        //发布登录事件
        EventModel eventModel = new EventModel();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("referer",referer);
        param.put("userAgent", userAgent);
        param.put("ip", ip);
        param.put("lastLoginTime", new Date());
        eventModel.setParam(param);
        eventModel.setMember(member);
        ac.publishEvent(new LoginEvent(eventModel));

        return RecordBean.success(null);
    }

    /**
     *  注册成功处理
     *
     * @param request
     * @param response
     * @param member
     * @param loginWay  登录方式 参见：LoginWayConstant
     */
    public RecordBean gotoReg(HttpServletRequest request, HttpServletResponse response, ApplicationContext ac, Member member, int loginWay){
        if(member == null){
            return RecordBean.error("注册失败， 请稍后在试");
        }

        //记录注册日志
        String ip = ReqUtils.getIp(request);
        String userAgent = request.getHeader("user-agent");
        String referer =  request.getHeader("Referer");
        String fr = request.getParameter("fr");

        int uid = member.getUid();
        //登录成功,存入登录用户UID(暂时使用session)
        request.getSession().setAttribute(GlobalConstant.SESSION_UID, uid);

        logger.info("用户注册成功-->uid:{}, ip: {} ,userAgent:{},referer：{},fr:{}",uid,ip,userAgent,referer,fr);

        //发布注册送积分事件
        EventModel eventModel = new EventModel();
        Map<String, Object> param = new HashMap<>(1);
        param.put("taskCode", "RegisterTask");
        eventModel.setParam(param);
        eventModel.setMember(member);
        ac.publishEvent(new TaskEvent(eventModel));

        return RecordBean.success("success");
    }

	/**
	 * 登出
	 *
	 * @param request
	 * @param response
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response, String reason){
		Object uidObj = request.getSession().getAttribute(GlobalConstant.SESSION_UID);
		String token = CookieUtil.getCookie(request, GlobalConstant.COOKIE_KEY);

//		logger.info("用户退出操作 uid:" + uidObj + ",token:" + token + ",reason:" + reason);
//		if(memberService == null){
//			 memberService = SpringFactory.getBean();
//		}
//		//登出token
//		memberService.logoutAll(token, reason);

		// 清除session
		request.getSession().removeAttribute(GlobalConstant.SESSION_UID);

		// 清除用户cookie
		CookieUtil.removeCookie(request, response, GlobalConstant.COOKIE_KEY);

		//使session失效
		request.getSession().invalidate();

	}


    /**
     * 判断是否已经授权，如果是直接返回网站uid
     * @param uuid
     * @return
     */
    public RecordBean<Member> boforeReg(String uuid,Integer loginWay){
        Member member = iMemberService.getMemberByUuid(uuid,loginWay);
        if (member == null) {
            return RecordBean.error("error!");
        }
        return RecordBean.success("success!",member);
    }
}
