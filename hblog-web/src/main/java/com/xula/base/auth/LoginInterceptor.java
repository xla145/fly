package com.xula.base.auth;

import com.xula.base.utils.JsonBean;
import com.xula.base.utils.WebReqUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登录过滤
 * 
 * @author xla
 *
 */
public class LoginInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
	        HandlerMethod method = (HandlerMethod)handler;
	        Login login = method.getMethod().getDeclaringClass().getAnnotation(Login.class);
	        if (login == null) {  
	        	login = method.getMethodAnnotation(Login.class);  
	        }
	       
	        if(login == null){
	        	return true;
	        }
	        
	        boolean immed = login.immed();
	        String simpleName = method.getMethod().getReturnType().getSimpleName();

	        //判断登录态
	        int uid = WebReqUtils.getSessionUid(request);
	        // 登录成功
			if (uid > 0) {
				return true;
			}

			String loginUrl = contextPath(request) + "user/login";
			//立即登录
			if(immed){
				String fr = request.getParameter("fr");
				String quertString = request.getQueryString();
				String referrer = request.getRequestURL().toString();
				if(StringUtils.isNotBlank(quertString)){
					referrer = referrer + "?" + quertString;
				}
				referrer = URLEncoder.encode(referrer, "utf-8");
				if(StringUtils.isBlank(fr)){
					response.sendRedirect(loginUrl + "?referrer=" + referrer);
				}else{
					response.sendRedirect(loginUrl + "?fr=" + fr + "&referrer=" + referrer);
				}
			}
			//ajax请求
			else if("JSONObject".equalsIgnoreCase(simpleName)){
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "application/json;charset=UTF-8");
				response.getWriter().write(JsonBean.notLogin("未登录", loginUrl).toString());
			}
			//http资源请求
			else{
				response.sendRedirect("/notice?state=notlogin");
			}
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

	/**
	 * 上下文环境地址
	 * @param request
	 * @return
	 */
	private static String contextPath(HttpServletRequest request){
		int port = request.getServerPort();
		String contextPath = request.getScheme()+"://"+request.getServerName();
		if(port != 80 && port != 443){
			contextPath = contextPath + ":" + port + "/";
		}else{
			contextPath = contextPath + "/";
		}
		return contextPath;
	}
	
}
