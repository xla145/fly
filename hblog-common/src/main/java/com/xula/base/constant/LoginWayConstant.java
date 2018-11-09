package com.xula.base.constant;


/**
 * 用户登录
 * @author xla
 */
public enum LoginWayConstant {


	/**
	 * 使用邮箱登录
	 */
	email(1,"使用邮箱登录"),
	/**
	 * qq授权登录
	 */
	qq(2,"qq授权登录"),
	/**
	 * 微博授权登录
	 */
	weibo(3,"微博授权登录");

	/**
	 * 登录方式
	 */
	private int way;
	/**
	 * 登录描述
	 */
	private String describe;


	LoginWayConstant(int way, String describe) {
		this.way = way;
		this.describe = describe;
	}

	public static String getDescribe(int way){
		LoginWayConstant[] arr = LoginWayConstant.values();
		for (LoginWayConstant lwc : arr) {
			if (lwc.getWay() == way) {
				return lwc.getDescribe();
			}
		}
		return "";
	}

	public int getWay() {
		return way;
	}
	public void setWay(int way) {
		this.way = way;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
