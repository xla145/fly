package com.xula.entity.sms;
import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Table;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 邮箱模板
 */
@Table(name = "email_conf")
public class SmsConf extends BasePojo {

	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;				//主键
	private String cmd;				//标记
	private String planClass;		//类名
	private String temp;			//模板
	private String aisle;			//通道
	private String tempContent;	//模板内容
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getPlanClass() {
		return planClass;
	}
	public void setPlanClass(String planClass) {
		this.planClass = planClass;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getAisle() {
		return aisle;
	}
	public void setAisle(String aisle) {
		this.aisle = aisle;
	}
	public String getTempContent() {
		return tempContent;
	}
	public void setTempContent(String tempContent) {
		this.tempContent = tempContent;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
