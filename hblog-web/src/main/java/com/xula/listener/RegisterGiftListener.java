//package com.xula.listener;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yuelinghui.base.config.GlobalConfig;
//import com.yuelinghui.base.utils.DoubleOperUtil;
//import com.yuelinghui.event.EventModel;
//import com.yuelinghui.event.RegisterEvent;
//import com.yuelinghui.service.dict.api.IDictService;
//import com.yuelinghui.service.helper.DataBean;
//import com.yuelinghui.service.member.api.ICouponService;
//import com.yuelinghui.service.member.vo.Member;
//import com.yuelinghui.service.member.vo.coupon.SendCouponResult;
//import com.yuelinghui.service.sms.api.ISmsService;
//import com.yuelinghui.service.sms.vo.SmsConf;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * 注册完成 --送新手礼包
// *
// * @author caixb
// *
// */
//@Component
//public class RegisterGiftListener implements ApplicationListener<RegisterEvent>{
//	Logger logger = LoggerFactory.getLogger(RegisterGiftListener.class);
//
//	@Autowired
//	private ICouponService couponService;
//	@Autowired
//	private ISmsService smsService;
//	@Autowired
//	private IDictService dictService;
//
//
//    @Override
//    public void onApplicationEvent(final RegisterEvent event) {
//        if(event instanceof RegisterEvent) {
//        	EventModel eventModel = (EventModel)event.getSource();
//        	if(eventModel == null){
//        		return;
//        	}
//        	Member member = eventModel.getMember();
//        	if(member == null){
//        		return;
//        	}
//        	int uid = member.getUid();
//
//        	//发送新人代金券
//        	try {
//	        	String reason = "新人注册赠送代金券";
//
//	        	String acId = "NEWBIE_GIFT";
//	        	String[] couponIds = dictService.getStringValues("BOON_COUPON", "register", ",");
//
//	        	if(couponIds != null && couponIds.length > 0){
//	        		DataBean<List<SendCouponResult>> bean = couponService.sendCoupon(uid, couponIds, reason, acId);
//	        		if(GlobalConfig.dev){
//        				logger.info("[新人注册赠送代金券结果:"+ bean.isOk() + "], result:" + bean.getMsg() + ",uid:" + uid + ",couponIds:" + couponIds + ",acId:" + acId + ", detail:" + JSONObject.toJSONString(bean.getData()));
//        			}
//
//	        		//成功后短信通知
//	        		if(bean.isOk() && StringUtils.isNotBlank(member.getMobile())){
//	        			double totalAmount = 0.d;
//	        			List<SendCouponResult> list = bean.getData();
//	        			for (SendCouponResult scr : list) {
//	        				if(scr.getMcidAmount() > 0){
//	        					totalAmount = DoubleOperUtil.sum(totalAmount, scr.getMcidAmount());
//	        				}
//						}
//	        			if(totalAmount > 0){
//	        				JSONObject params = new JSONObject();
//							params.put("num", totalAmount);
//
//							SmsConf smsConf = smsService.getSmsConfByCmd("coupon");
//							smsService.sendSms(member.getMobile(), smsConf.getTemp(), params);
//	        			}
//	        		}
//	        	}
//			} catch (Exception e) {
//				logger.error("新人注册赠送代金券 异常", e);
//			}
//        }
//    }
//}