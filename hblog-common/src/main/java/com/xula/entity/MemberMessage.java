package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @author XLA
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MemberMessage extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * UID
	 */
	private Integer id;
	/**
	 * 发送消息的用户ID
	 */
	private Integer fromUid;
	/**
	 * 接收消息的用户ID
	 */
	private Integer toUid;
	/**
	 * 消息可能关联的帖子
	 */
	private String articleId;
	/**
	 * 消息可能关联的评论
	 */
	private Integer commentId;
	/**
	 * 消息内容
	 */
	private String content;
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
