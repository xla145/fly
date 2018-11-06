package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author XXXX
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
public class MemberMessage extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//发送消息的用户ID
	private Integer fromUid;
	//接收消息的用户ID
	private Integer toUid;
	//消息可能关联的帖子
	private Integer articleId;
	//消息可能关联的评论
	private Integer commentId;
	//消息内容
	private String content;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
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
	 * 设置：发送消息的用户ID
	 */
	public void setFromUid(Integer fromUid) {
		this.fromUid = fromUid;
	}
	/**
	 * 获取：发送消息的用户ID
	 */
	public Integer getFromUid() {
		return fromUid;
	}
	/**
	 * 设置：接收消息的用户ID
	 */
	public void setToUid(Integer toUid) {
		this.toUid = toUid;
	}
	/**
	 * 获取：接收消息的用户ID
	 */
	public Integer getToUid() {
		return toUid;
	}
	/**
	 * 设置：消息可能关联的帖子
	 */
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：消息可能关联的帖子
	 */
	public Integer getArticleId() {
		return articleId;
	}
	/**
	 * 设置：消息可能关联的评论
	 */
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	/**
	 * 获取：消息可能关联的评论
	 */
	public Integer getCommentId() {
		return commentId;
	}
	/**
	 * 设置：消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：消息内容
	 */
	public String getContent() {
		return content;
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
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
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
