package com.xula.service.sms;
import com.alibaba.fastjson.JSONObject;
import com.xula.entity.sms.SmsConf;

import java.util.List;


/**
 * 
 * 发送短信验证码
 * 
 * @author caixb
 *
 */
public interface ISmsService {
	
	/**
	 * 根据业务编码查询短信配置
	 * 
	 * @param cmd 业务编码
	 * @return
	 */
	public SmsConf getSmsConfByCmd(String cmd);
	
	/**
	 * 短信验证码校验
	 * 
	 * @param mobile
	 * @param tempId
	 * @param vcode
	 * @return
	 */
	public boolean verifyCode(String mobile, String tempId, String vcode);

	
	/**
	 * 短信验证码发送
	 * 
	 * @param mobile
	 * @param tempId
	 * @return
	 */
	public boolean sendVcode(String mobile, String tempId);
	
	/**
	 * 通知类短信发送
	 * 
	 * @param mobile
	 * @param tempId
	 * @param params
	 * @return
	 */
	public boolean sendSms(String mobile, String tempId, JSONObject params);

	/**
	 * 批量发送通知类短信
	 *
	 * @param mobiles
	 * @param tempId
	 * @param paramsList
	 * @return
	 */
	public boolean sendBatchSms(List<String> mobiles, String tempId, List<JSONObject> paramsList);
	
}
