package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.util.Date;

/**
 * 
 * token
 *
 * @author caixb
 */
public class MemberToken extends BasePojo {
	
	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer uid;
	private String token;                           
	private Date loginTime;                    
	private Date tenancyTerm;                     
	private Integer status;  
	private String loginIp;                        
	private String loginAddress;                    
	private String loginWay;                    
	private String remark;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getTenancyTerm() {
		return tenancyTerm;
	}
	public void setTenancyTerm(Date tenancyTerm) {
		this.tenancyTerm = tenancyTerm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getLoginAddress() {
		return loginAddress;
	}
	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}
	public String getLoginWay() {
		return loginWay;
	}
	public void setLoginWay(String loginWay) {
		this.loginWay = loginWay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
