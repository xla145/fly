package com.xula.base.constant;


/**
 * 用户状态
 * @author xla
 */
public enum LoginWayConstant {


	/**
	 * 使用邮箱登录
	 */
	email("email","使用邮箱登录"),
	/**
	 * qq授权登录
	 */
	qq("qq","qq授权登录"),
	/**
	 * 微博授权登录
	 */
	weibo("weibo","微博授权登录");

	/**
	 * 登录方式
	 */
	private String way;
	/**
	 * 登录描述
	 */
	private String describe;


	LoginWayConstant(String way, String describe) {
		this.way = way;
		this.describe = describe;
	}

	public static String getDescribe(String way){
		LoginWayConstant[] arr = LoginWayConstant.values();
		for (LoginWayConstant lwc : arr) {
			if (lwc.getWay().equalsIgnoreCase(way)) {
				return lwc.getDescribe();
			}
		}
		return "";
	}
	
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
