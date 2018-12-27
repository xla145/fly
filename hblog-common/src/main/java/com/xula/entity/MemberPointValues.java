package com.xula.entity;

import cn.assist.easydao.annotation.Table;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Table(name = "member_grow_log")
@Data
@EqualsAndHashCode(callSuper=false)
public class MemberPointValues extends BasePojo {
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

}
