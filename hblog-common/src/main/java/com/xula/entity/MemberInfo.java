package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;


/**
 * 
 * 
 * @author XXXX
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
public class MemberInfo extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * uid
	 */
	private Integer uid;
	/**
	 * 会员当前vip等级
	 */
	private Integer vip;
	/**
	 * 会员当前vip等级名
	 */
	private String vipName;
	/**
	 * 会员当前成长值
	 */
	private Long growthValue;
	/**
	 * 会员当前积分
	 */
	private Long pointValue;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
