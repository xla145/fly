package com.xula.entity.sms;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;

/**
 * 
 * 邮箱记录
 *
 * @author xla
 */
@Data
public class SmsRecord extends BasePojo {

	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	/**
	 * 类型 1： 验证码类  2：通知类
	 */
	private Integer type;
	/**
	 * 发送的手机号或者邮箱号
	 */
	private String number;
	private Integer status;
	private String tempId;
	private String tempContent;
	/**
	 * 使用方
	 */
	private String provider;
	private Date sendTime;
	private Date useTime;
	private Date expireTime;
	private String remark;
}
