package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 文章浏览
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
public class ArticleBrowse extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	//浏览文章用户
	private Integer uid;
	//文章编号
	private Integer articleId;
	//创建时间
	private Date createTime;
	//备注
	private String reamark;

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
	 * 设置：浏览文章用户
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	/**
	 * 获取：浏览文章用户
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
	public void setReamark(String reamark) {
		this.reamark = reamark;
	}
	/**
	 * 获取：备注
	 */
	public String getReamark() {
		return reamark;
	}
}
