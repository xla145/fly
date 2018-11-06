package com.xula.base.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie操作
 *
 * @author xla
 * 
 */
public class CookieUtil {

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name 		cookie名字
	 * @param value 	cookie值
	 * @param maxAge 	cookie生命周期 以秒为单位
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 将cookie里的值删掉
	 * 
	 * @param request
	 * @param name  要删除的键
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			cookie.setPath("/");
			cookie.setValue(null);
			cookie.setMaxAge(0);// 立即过期
			response.addCookie(cookie);
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
