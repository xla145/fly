package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;


/**
 * 文章
 * @author XLA
 * @email XLA@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
public class Article extends BasePojo {

    @Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章分类
	 */
	private Integer catId;
	/**
	 * 文章分类名称
	 */
	private String catName;
	/**
	 * 文章创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 文章浏览量
	 */
	private Long browse;
	/**
	 * 创建用户uid
	 */
	private Integer createUid;
	/**
	 * 文章内容
	 */
	private String info;
	/**
	 * 发布文章消耗积分 10 - 200 之间（后台字典配置）
	 */
	private Integer payPoint;
	/**
	 * 状态  -1：已删除 0：待审核 5：成功发布 10：未结 15：完结 20：不通过
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 标签
	 */
	private String label;
	/**
	 * 权重
	 */
	private Integer weight;
	/**
	 * 是否置顶
	 */
	private Integer isTop;
	/**
	 * 是否支持评论
	 */
	private Integer isComment;

	/**
	 * 状态对应的名称
	 */
	@Temporary
	private String statusName;
}
