package com.xula.entity.task;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户pojo
 *
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class VipGrade extends BasePojo {

	@Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer vid;
	private Integer vip;
	private String name;
	private String info;
	private String icon;
	private int growthValue;
	private Date createTime;
	private String remark;
}
