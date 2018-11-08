package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;


/**
 * 
 * 文章评论
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
public class ArticleComment extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 评论的文章
	 */
	private Integer articleId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论用户uid
	 */
	private Integer uid;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
}
