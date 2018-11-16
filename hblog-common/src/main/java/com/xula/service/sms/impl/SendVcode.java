//package com.xula.service.sms.impl;
//
//
//import com.xula.base.utils.RecordBean;
//import com.xula.entity.sms.VcodeModel;
//import com.xula.service.member.IMemberService;
//import com.xula.service.sms.ISmsService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * 发送短信
// *
// * @author caixb
// *
// */
//public abstract class SendVcode {
//
//	@Autowired
//	protected IMemberService memberService;
//	@Autowired
//	protected ISmsService smsService;
//
//	/**
//	 * 校验图片验证码
//	 *
//	 * @param vcodeModel
//	 * @return
//	 */
//	public RecordBean<String> verifyImgVcode(VcodeModel vcodeModel){
//		String inputImgVcode = vcodeModel.getInputImgVcode();
//		String localImgVcode = vcodeModel.getLocalImgVcode();
//		if(StringUtils.isBlank(inputImgVcode)){
//			return RecordBean.error("图片验证码不能为空");
//		}
//		if(!inputImgVcode.equals(localImgVcode.toLowerCase())){
//			return RecordBean.error("图片验证码不正确");
//		}
//		return RecordBean.success("ok");
//	}
//
//	/**
//	 *
//	 * 校验手机号码是否存在
//	 *
//	 * @param vcodeModel
//	 * @return
//	 */
//	public boolean verifyMobileExists(VcodeModel vcodeModel){
//		String mobile = vcodeModel.getMobile();
//		if(StringUtils.isBlank(mobile)){
//			return false;
//		}
//		Member member = memberService.findMemberByMobile(mobile);
//		if(member != null){
//			return true;
//		}
//		return false;
//	}
//
//	public abstract DataBean<String> sendVcode(VcodeModel vcodeModel);
//}
