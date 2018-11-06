
package com.xula.service.dict.vo;

import cn.assist.easydao.annotation.Table;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 字典项
*/
@Table(name = "dict_item")
public class Item extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	
	private String groupCode;
	private String name;
	private String value;
	private String info;

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}

