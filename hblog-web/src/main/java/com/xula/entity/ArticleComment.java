package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 文章评论
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
public class ArticleComment extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	/**
	 * 评论的文章
	 */
	private Integer articleId;
	//评论内容
	private String content;
	//评论用户uid
	private Integer uid;
	//创建时间
	private Date createTime;
	//备注
	private String remark;

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
	 * 设置：评论的文章
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：评论的文章
	 */
	public Integer getArticleId() {
		return articleId;
	}
	/**
	 * 设置：评论内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：评论内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：评论用户uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：评论用户uid
	 */
	public Integer getUid() {
		return uid;
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
}
