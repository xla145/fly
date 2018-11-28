package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Table;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;


/**
 * 文章分类
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Table(name = "article_category")
@Data
public class Category extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 状态 0：停用 1：启用
	 */
	private Integer status;

	/**
	 * 是否是热门
	 */
	private Integer isHot;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
}
