package com.xula.entity.sms;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * 发送短信model
 * 
 * @author caixb
 *
 */
public class VcodeModel {

	public static final String DEFAULT_TEMP_CODE = "SMS_1445015"; // 普通短信验证码模板code
																	// 默认
	private String mobile; // 手机号
	private String inputImgVcode; // 用户输入的图片验证码
	private String localImgVcode; // 服务器缓存的图片验证码
	private String temp; // 短信验证码模板Id
	private JSONObject smsParam; // 短信参数，短信模板内容中设置的${param}参数，如{"user":"little girl","gameName":"时空猎人"}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getInputImgVcode() {
		return inputImgVcode;
	}

	public void setInputImgVcode(String inputImgVcode) {
		this.inputImgVcode = inputImgVcode;
	}

	public String getLocalImgVcode() {
		return localImgVcode;
	}

	public void setLocalImgVcode(String localImgVcode) {
		this.localImgVcode = localImgVcode;
	}

	public String getTemp() {
		if (StringUtils.isBlank(temp)) {
			return DEFAULT_TEMP_CODE;
		} else {
			return temp;
		}
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public JSONObject getSmsParam() {
		return smsParam;
	}

	public void setSmsParam(JSONObject smsParam) {
		this.smsParam = smsParam;
	}

}
