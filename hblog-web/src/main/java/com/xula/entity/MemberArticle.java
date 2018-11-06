package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户收藏文章表
 * 
 * @author XXXX
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
public class MemberArticle extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//收藏用户uid
	private Integer uid;
	//文章编号
	private Integer articleId;
	//文章标题
	private String articleTitle;
	//
	private Date createTime;
	//
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
	 * 设置：收藏用户uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：收藏用户uid
	 */
	public Integer getUid() {
		return uid;
	}
	/**
	 * 设置：文章编号
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：文章编号
	 */
	public Integer getArticleId() {
		return articleId;
	}
	/**
	 * 设置：文章标题
	 */
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	/**
	 * 获取：文章标题
	 */
	public String getArticleTitle() {
		return articleTitle;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
}
