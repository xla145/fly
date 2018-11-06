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
public class MemberGrowLog extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户uid
	private Integer uid;
	//成长值
	private Integer growthValue;
	//积分
	private Integer pointValue;
	//符号   1：表示添加， 2：表示减少
	private Integer symbol;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//操作人id
	private Integer operId;
	//备注
	private String remark;
	//来源
	private String source;
	//来源类型 1：签到 2：发表文章 3：后台赠送 4：其他
	private Integer type;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：用户uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户uid
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：成长值
	 */
	public void setGrowthValue(Integer growthValue) {
		this.growthValue = growthValue;
	}
	/**
	 * 获取：成长值
	 */
	public Integer getGrowthValue() {
		return growthValue;
	}
	/**
	 * 设置：积分
	 */
	public void setPointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}
	/**
	 * 获取：积分
	 */
	public Integer getPointValue() {
		return pointValue;
	}
	/**
	 * 设置：符号   1：表示添加， 2：表示减少
	 */
	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}
	/**
	 * 获取：符号   1：表示添加， 2：表示减少
	 */
	public Integer getSymbol() {
		return symbol;
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
	/**
	 * 设置：操作人id
	 */
	public void setOperId(Integer operId) {
		this.operId = operId;
	}
	/**
	 * 获取：操作人id
	 */
	public Integer getOperId() {
		return operId;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：来源
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * 获取：来源
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置：来源类型 1：签到 2：发表文章 3：后台赠送 4：其他
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：来源类型 1：签到 2：发表文章 3：后台赠送 4：其他
	 */
	public Integer getType() {
		return type;
	}
}
