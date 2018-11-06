//package com.xula.controller.auth;
//
//
//import com.xula.base.constant.GlobalConstant;
//import com.xula.base.helper.SpringFactory;
//import com.xula.base.utils.CookieUtil;
//import com.xula.base.utils.RecordBean;
//import com.xula.base.utils.ReqUtils;
//import com.xula.controller.BaseController;
//import com.xula.entity.Member;
//import com.xula.entity.MemberToken;
//import com.xula.event.EventModel;
//import com.xula.event.LoginEvent;
//import com.xula.event.RegisterEvent;
//import com.xula.service.member.IMemberService;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 登录、注册、登出 base方法
// *
// * @author caixb
// *
// */
//public class BaseAuth extends BaseController {
//	Logger logger = org.slf4j.LoggerFactory.getLogger(BaseAuth.class);
//	Logger loginLog = org.slf4j.LoggerFactory.getLogger("visit_login_log");
//	Logger registerLog = org.slf4j.LoggerFactory.getLogger("register_origin_log");
//
//	@Autowired
//	public IMemberService memberService;
//
//	public BaseAuth(){
//		memberService = SpringFactory.getBean("IMemberService");
//	}
//	/**
//	 *  登录成功处理
//	 *
//	 * @param request
//	 * @param response
//	 * @param member
//	 * @param loginWay  登录方式 参见：LoginWayConstant
//	 */
//	public RecordBean gotoLogin(HttpServletRequest request, HttpServletResponse response, ApplicationContext ac, Member member, String loginWay){
//		if(member == null){
//			return RecordBean.error("登录失败， 请稍后在试");
//		}
//		String userIp = ReqUtils.getIp(request);
//		//登录
//		MemberToken memberToken = memberService.login(member.getUid(), userIp, loginWay, (LoginWayConstant.getDescribe(loginWay)));
//		if(memberToken == null){
//			return RecordBean.error("登录失败， 请稍后在试");
//		}
//
//		int uid = member.getUid();
//		//登录成功,存入登录用户UID(暂时使用session)
//		request.getSession().setAttribute(GlobalConstant.SESSION_UID, uid);
//		//存入cookie 用户token
//		long from = memberToken.getLoginTime().getTime();
//		long to = memberToken.getTenancyTerm().getTime();
//		int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
//		int maxAge = days * 86400;
//		CookieUtil.setCookie(response, GlobalConstant.COOKIE_KEY, memberToken.getToken(), maxAge);
//
//
//		//记录登录日志
//		String ip = ReqUtils.getIp(request);
//		String userAgent = request.getHeader("user-agent");
//		String referer =  request.getHeader("Referer");
//		String fr = request.getParameter("fr");
//
//		loginLog.info("用户登录成功-->uid:" + uid + "ip:" + ip + ",userAgent:" + userAgent +
//				",referer：" + referer + ",fr:" + fr + ",token:" + memberToken.getToken() +
//				",tokenTenancyTerm:" + memberToken.getTenancyTerm());
//
//		//发布登录事件
//		EventModel eventModel = new EventModel();
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("referer",referer);
//		param.put("userAgent", userAgent);
//		param.put("ip", ip);
//		param.put("lastLoginTime", memberToken.getLoginTime());
//		eventModel.setParam(param);
//		eventModel.setMember(member);
//		ac.publishEvent(new LoginEvent(eventModel));
//
//		return RecordBean.success(null);
//	}
//
//	/**
//	 * 注册成功
//	 *
//	 * @param request
//	 * @param response
//	 * @param member
//	 */
//	public void registerSuccess(HttpServletRequest request, HttpServletResponse response, ApplicationContext ac, Member member){
//		if(member == null){
//			return;
//		}
//		EventModel eventModel = new EventModel();
//		int uid = member.getUid();
//		String ip = ReqUtils.getIp(request);
//		String userAgent = request.getHeader("user-agent");
//		String referer =  request.getHeader("Referer");
//		String vipCardNo = request.getParameter("vipCardNo");
//		String fr = request.getParameter("fr");
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("ip", ip);
//		param.put("userAgent", userAgent);
//		param.put("referer", referer);
//		param.put("fr", fr);
//		param.put("vipCardNo", vipCardNo);
//
//		eventModel.setMember(member);
//		eventModel.setRequest(request);
//		eventModel.setResponse(response);
//		eventModel.setParam(param);
//		registerLog.info("用户注册成功-->uid:" + uid + ",ip:" + ip + ",userAgent:" + userAgent +",referer:"+referer+",fr:" + fr);
//		ac.publishEvent(new RegisterEvent(eventModel));
//	}
//
//	/**
//	 * 登出
//	 *
//	 * @param request
//	 * @param response
//	 */
//	public void loginOut(HttpServletRequest request, HttpServletResponse response, String reason){
//		Object uidObj = request.getSession().getAttribute(GlobalConstant.SESSION_UID);
//		String token = CookieUtil.getCookie(request, GlobalConstant.COOKIE_KEY);
//
//		logger.info("用户退出操作 uid:" + uidObj + ",token:" + token + ",reason:" + reason);
//		if(memberService == null){
//			 memberService = SpringFactory.getBean(MemberServiceImpl.class);
//		}
//		//登出token
//		memberService.logoutAll(token, reason);
//
//		// 清除session
//		request.getSession().removeAttribute(GlobalConstant.SESSION_UID);
//
//		// 清除用户cookie
//		CookieUtil.removeCookie(request, response, GlobalConstant.COOKIE_KEY);
//
//		//使session失效
//		request.getSession().invalidate();
//
//	}
//}
