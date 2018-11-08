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
public class MemberLog extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * uid
	 */
	private Integer uid;
	/**
	 * 操作名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
}
