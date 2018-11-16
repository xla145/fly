//package com.xula.service.sms.impl;
//
//import cn.assist.easydao.common.Conditions;
//import cn.assist.easydao.common.SqlExpr;
//import cn.assist.easydao.common.SqlJoin;
//import cn.assist.easydao.dao.BaseDao;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.xula.entity.sms.SmsConf;
//import com.xula.entity.sms.SmsRecord;
//import com.xula.service.dict.IDictService;
//import com.xula.service.sms.ISmsService;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * 短信服务
// *
// * @author caixb
// *
// */
//@Service("ISmsService")
//public class SmsServiceImpl implements ISmsService {
//	Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
//
//    private static final String product = "Dysmsapi"; //产品名称:云通信短信API产品
//    private static final String domain = "dysmsapi.aliyuncs.com"; //产品域名
//
//    @Autowired
//    private IDictService dictService;
//
//    /**
//	 * 根据业务编码查询短信配置
//	 *
//	 * @param cmd 业务编码
//	 * @return
//	 */
//	public SmsConf getSmsConfByCmd(String cmd){
//		Conditions conn = new Conditions("cmd", SqlExpr.EQUAL, cmd);
//		conn.add(new Conditions("status", SqlExpr.EQUAL, 10), SqlJoin.AND);
//		SmsConf smsConf = BaseDao.dao.queryForEntity(SmsConf.class, conn);
//
////		if(smsConf == null){
////			smsConf = getSmsConfByCmd("invalid");
////		}
//		if(smsConf == null){
//			smsConf = new SmsConf();
//		}
//		return smsConf;
//	}
//
//	/**
//	 * 短信验证码校验
//	 *
//	 * @param mobile
//	 * @param tempId
//	 * @param vcode
//	 * @return
//	 */
//	@Override
//	public boolean verifyCode(String mobile, String tempId, String vcode) {
//		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(tempId) || StringUtils.isBlank(vcode)){
//			return false;
//		}
//		try {
//			String localVcode = this.getCacheVcode(mobile, tempId);
//			if(StringUtils.isBlank(localVcode)){
//				return false;
//			}
//			if(localVcode.equalsIgnoreCase(vcode)){
//				this.useCacheVcode(mobile, tempId, vcode);
//				return true;
//			}
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		return false;
//	}
//
//	/**
//	 * 短信验证码发送
//	 *
//	 * @param mobile
//	 * @param tempId
//	 * @return
//	 */
//	@Override
//	public boolean sendVcode(String mobile, String tempId) {
////		String localVcode = this.getCacheVcode(mobile, tempId);
////		if(StringUtils.isNotBlank(localVcode)){
////			return true;
////		}
//		this.expiryCacheVcode(mobile, tempId);
//
//		String vcode = IDBuilder.getNumberRandom(4);
//		try {
//			JSONObject params = new JSONObject();
//			params.put("code", vcode);
//			SendSmsResponse sendSmsResponse = null;
//			//根据code和name来获取value(手机号)
//			String[] whiteList = dictService.getStringValues("SMS_WHITELIST", "mobile", ",");
//			if(whiteList != null && Arrays.asList(whiteList).contains(mobile)){//如果包含此手机号
//				sendSmsResponse = new SendSmsResponse();
//				sendSmsResponse.setCode("OK");
//			}else{
//				sendSmsResponse = send(mobile, tempId, params);
//			}
//	        if(sendSmsResponse != null && "OK".equalsIgnoreCase(sendSmsResponse.getCode())){
//	        	this.addCacheVcode(mobile, tempId, vcode, dictService.getIntegerValue("ALIYUN_SMS", "expiry", 180));
//	        	logger.info("发送短信成功-->mobile:" + mobile + ", vcode:" + vcode);
//	        	return true;
//	        }else{
//	        	logger.error("短信发送失败：mobile:" + mobile + ",vcode:" + vcode + ",result:" + JSON.toJSONString(sendSmsResponse));
//	        }
//
//		} catch (Exception e) {
//			logger.error("短信发送异常：",  e);
//		}
//		return false;
//	}
//
//	/**
//	 * 通知类短信发送
//	 *
//	 * @param mobile
//	 * @param tempId
//	 * @param params
//	 * @return
//	 */
//	@Override
//	public boolean sendSms(String mobile, String tempId, JSONObject params) {
//		SendSmsResponse sendSmsResponse = send(mobile, tempId, params);
//		if(sendSmsResponse != null && "OK".equalsIgnoreCase(sendSmsResponse.getCode())){
//			this.addSmsRecord(mobile, tempId, (params == null ? "" : params.toJSONString()));
//        	return true;
//        }else{
//        	logger.error("短信发送失败：mobile:" + mobile + ",tempId:" + tempId + ",params:" + params + ",sendSmsResponse:" + JSON.toJSONString(sendSmsResponse));
//        }
//		return false;
//	}
//
//
//	public boolean sendBatchSms(List<String> mobiles, String tempId, List<JSONObject> paramsList) {
//
//		SendBatchSmsResponse sendSmsResponse = sendBatch( mobiles, tempId, paramsList);
//		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//			this.addBatchSmsRecord(mobiles,  tempId, paramsList);
//			return true;
//
//		} else {
//			logger.error("短信发送失败：mobile:" + JSON.toJSONString(mobiles) + ",tempId:" + tempId + ",params:" + JSON.toJSONString(paramsList) + ",sendSmsResponse:" + JSON.toJSONString(sendSmsResponse));
//		}
//		return false;
//	}
//
//
//	/**
//	 * 短信发送
//	 *
//	 * @param mobile
//	 * @param tempId
//	 * @param params
//	 * @return
//	 */
//	private SendSmsResponse send(String mobile, String tempId, JSONObject params) {
//		try {
//			//可自助调整超时时间
//	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//	        //初始化acsClient,暂不支持region化
//	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", dictService.getStringValue("ALIYUN_SMS", "accessKeyId"), dictService.getStringValue("ALIYUN_SMS", "accessKeySecret"));
//	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//	        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//	        //组装请求对象-具体描述见控制台-文档部分内容
//	        SendSmsRequest request = new SendSmsRequest();
//	        //必填:待发送手机号
//	        request.setPhoneNumbers(mobile);
//	        //必填:短信签名-可在短信控制台中找到
//	        request.setSignName(dictService.getStringValue("ALIYUN_SMS", "sign"));
//	        //必填:短信模板-可在短信控制台中找到
//	        request.setTemplateCode(tempId);
//	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//	        if(params != null){
//	        	request.setTemplateParam(params.toJSONString());
//	        }
//	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//	        //request.setOutId("test");
//
//	        //hint 此处可能会抛出异常，注意catch
//	        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//	        return sendSmsResponse;
//
//		} catch (Exception e) {
//			logger.error("短信发送异常：",  e);
//		}
//		return null;
//	}
//
//	/**
//	 * 批量短信发送
//	 *
//	 * @param mobiles
//	 * @param tempId
//	 * @param paramsList
//	 * @return
//	 */
//	private SendBatchSmsResponse sendBatch(List<String> mobiles, String tempId, List<JSONObject> paramsList) {
//		try {
//			if(CollectionUtils.isEmpty(mobiles)){
//				throw new Exception("手机号码不能为空");
//			}
//			List<String> signNames = Lists.newArrayList();
//			for(String mobile : mobiles){
//				signNames.add(dictService.getStringValue("ALIYUN_SMS", "sign"));
//			}
//
//			//可自助调整超时时间
//			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//			//初始化acsClient,暂不支持region化
//			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", dictService.getStringValue("ALIYUN_SMS", "accessKeyId"), dictService.getStringValue("ALIYUN_SMS", "accessKeySecret"));
//			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//			IAcsClient acsClient = new DefaultAcsClient(profile);
//
//			//组装请求对象
//			SendBatchSmsRequest request = new SendBatchSmsRequest();
//
//			//使用post提交
//			request.setMethod(MethodType.POST);
//			//必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
//			request.setPhoneNumberJson(JSON.toJSONString(mobiles));
//			//必填:短信签名-支持不同的号码发送不同的短信签名
//
//			request.setSignNameJson(JSON.toJSONString(signNames));
//			//必填:短信模板-可在短信控制台中找到
//			request.setTemplateCode(tempId);
//			//必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//			//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//			request.setTemplateParamJson(JSON.toJSONString(paramsList));
//			//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
//			//request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");
//			//请求失败这里会抛ClientException异常
//			SendBatchSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//			//请求成功
////			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
////
////			}
//			return sendSmsResponse;
//
//		} catch (Exception e) {
//			logger.error("短信发送异常：",  e);
//		}
//		return null;
//	}
//
//	/**
//	 * 保存短信记录
//	 *
//	 * @param mobile		手机号
//	 * @param tempId		模板id
//	 * @param params		参数
//	 * @return
//	 */
//	private boolean addSmsRecord(String mobile, String tempId, String params){
//		Date date = new Date();
//		SmsRecord smsRecord = new SmsRecord();
//		smsRecord.setType(SmsConstant.SMS_TYPE_NOTICE);
//		smsRecord.setMobile(mobile);
//		smsRecord.setVcode("-");
//		smsRecord.setTempId(tempId);
//		smsRecord.setTempContent(params);
//		smsRecord.setProvider(product);
//		smsRecord.setSendTime(date);
//		smsRecord.setExpireTime(date);
//		smsRecord.setStatus(SmsConstant.SMS_STATUS_USED);
//		smsRecord.setUseTime(date);
//		smsRecord.setRemark(product);
//		return BaseDao.dao.insert(smsRecord) == 1;
//	}
//
//
//	/**
//	 * 保存短信记录
//	 *
//	 * @param mobiles		手机号
//	 * @param tempId		模板id
//	 * @param params		参数
//	 * @return
//	 */
//	private boolean addBatchSmsRecord(List<String> mobiles, String tempId, List<JSONObject> params) {
//		List<SmsRecord> list = Lists.newArrayList();
//		if(params.size() != mobiles.size()){
//			logger.error("mobiles和params的数量一样");
//			return false;
//		}
//		for(String mobile : mobiles){
//			Date date = new Date();
//			SmsRecord smsRecord = new SmsRecord();
//			smsRecord.setType(SmsConstant.SMS_TYPE_NOTICE);
//			smsRecord.setMobile(mobile);
//			smsRecord.setVcode("-");
//			smsRecord.setTempId(tempId);
//
//			smsRecord.setProvider(product);
//			smsRecord.setSendTime(date);
//			smsRecord.setExpireTime(date);
//			smsRecord.setStatus(SmsConstant.SMS_STATUS_USED);
//			smsRecord.setUseTime(date);
//			smsRecord.setRemark(product);
//			list.add(smsRecord);
//		}
//		int i =0;
//		for(JSONObject param : params){
//			list.get(i).setTempContent(param == null ? "" : param.toJSONString());
//			i++;
//		}
//		return BaseDao.dao.insert(list) == 1;
//	}
//
//
//
//
//	/**
//	 * 保存vcode到缓存
//	 *
//	 * @param mobile		手机号
//	 * @param tempId		模板id
//	 * @param vcode			验证码
//	 * @param expireTime	逾期时间 秒为单位
//	 * @return
//	 */
//	private boolean addCacheVcode(String mobile, String tempId, String vcode, int expireTime){
//
//		Date date = new Date();
//		SmsRecord smsRecord = new SmsRecord();
//		smsRecord.setType(SmsConstant.SMS_TYPE_VCODE);
//		smsRecord.setMobile(mobile);
//		smsRecord.setTempId(tempId);
//		smsRecord.setVcode(vcode);
//		smsRecord.setProvider(product);
//		smsRecord.setSendTime(date);
//		smsRecord.setExpireTime(DateUtils.addSeconds(date, expireTime));
//		smsRecord.setRemark(product);
//		return BaseDao.dao.insert(smsRecord) == 1;
//	}
//
//	/**
//	 * 获取缓存中的验证码
//	 *
//	 * @param mobile		手机号
//	 * @param tempId		模板id
//	 * @return
//	 */
//	private String getCacheVcode(String mobile, String tempId){
//		Conditions conn = new Conditions("mobile", SqlExpr.EQUAL, mobile);
//		conn.add(new Conditions("temp_id", SqlExpr.EQUAL, tempId), SqlJoin.AND);
//		conn.add(new Conditions("status", SqlExpr.EQUAL, SmsConstant.SMS_STATUS_UNUSED), SqlJoin.AND);
//		conn.add(new Conditions("expire_time", SqlExpr.GT, new Date()), SqlJoin.AND);
//		conn.add(new Conditions("type", SqlExpr.EQUAL, SmsConstant.SMS_TYPE_VCODE), SqlJoin.AND);
//
//		SmsRecord smsRecord = BaseDao.dao.queryForEntity(SmsRecord.class, conn);
//		if(smsRecord != null){
//			return smsRecord.getVcode();
//		}
//		return null;
//	}
//
//	/**
//	 * 缓存中的验证码使用
//	 *
//	 * @param mobile		手机号
//	 * @param tempId		模板id
//	 * @param vcode			短信验证码
//	 * @return
//	 */
//	private boolean useCacheVcode(String mobile, String tempId, String vcode){
//		SmsRecord smsRecord = new SmsRecord();
//		smsRecord.setStatus(SmsConstant.SMS_STATUS_USED);
//		smsRecord.setRemark("验证码已使用");
//		smsRecord.setUseTime(new Date());
//
//		Conditions conn = new Conditions("mobile", SqlExpr.EQUAL, mobile);
//		conn.add(new Conditions("temp_id", SqlExpr.EQUAL, tempId), SqlJoin.AND);
//		conn.add(new Conditions("vcode", SqlExpr.EQUAL, vcode), SqlJoin.AND);
//		conn.add(new Conditions("type", SqlExpr.EQUAL, SmsConstant.SMS_TYPE_VCODE), SqlJoin.AND);
//
//		return BaseDao.dao.update(smsRecord, conn) == 1;
//	}
//
//	/**
//	 * 缓存中的验证码过期
//	 *
//	 * @param mobile		手机号
//	 * @param tempId		模板id
//	 * @return
//	 */
//	private boolean expiryCacheVcode(String mobile, String tempId){
//		SmsRecord smsRecord = new SmsRecord();
//		smsRecord.setStatus(SmsConstant.SMS_STATUS_EXPIRE);
//		smsRecord.setRemark("验证码过期");
//
//		Conditions conn = new Conditions("mobile", SqlExpr.EQUAL, mobile);
//		conn.add(new Conditions("temp_id", SqlExpr.EQUAL, tempId), SqlJoin.AND);
//		conn.add(new Conditions("expire_time", SqlExpr.LT, new Date()), SqlJoin.AND);
//		conn.add(new Conditions("type", SqlExpr.EQUAL, SmsConstant.SMS_TYPE_VCODE), SqlJoin.AND);
//
//
//		return BaseDao.dao.update(smsRecord, conn) == 1;
//	}
//}
