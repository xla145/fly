package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;

/**
 * 成长积分日志表
*/
@Data
public class MemberGrowLog extends BasePojo {

	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private Integer uid;
	private Integer growthValue;
	private Integer pointValue;
	private Integer symbol;
	private Date createTime;
	private Date updateTime;
	private Integer operId;
	private String remark;
	private String source;
	/**
     * 来源类型 1：签到 2：发表文章 3：后台赠送 4：其他
	 */
	private Integer type;
}

