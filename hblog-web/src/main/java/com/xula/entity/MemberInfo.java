package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author XXXX
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
public class MemberInfo extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	//
	private Integer uid;
	//会员当前vip等级
	private Integer vip;
	//会员当前vip等级名
	private String vipName;
	//会员当前成长值
	private Long growthValue;
	//会员当前积分
	private Long pointValue;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：会员当前vip等级
	 */
	public void setVip(Integer vip) {
		this.vip = vip;
	}
	/**
	 * 获取：会员当前vip等级
	 */
	public Integer getVip() {
		return vip;
	}
	/**
	 * 设置：会员当前vip等级名
	 */
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	/**
	 * 获取：会员当前vip等级名
	 */
	public String getVipName() {
		return vipName;
	}
	/**
	 * 设置：会员当前成长值
	 */
	public void setGrowthValue(Long growthValue) {
		this.growthValue = growthValue;
	}
	/**
	 * 获取：会员当前成长值
	 */
	public Long getGrowthValue() {
		return growthValue;
	}
	/**
	 * 设置：会员当前积分
	 */
	public void setPointValue(Long pointValue) {
		this.pointValue = pointValue;
	}
	/**
	 * 获取：会员当前积分
	 */
	public Long getPointValue() {
		return pointValue;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
