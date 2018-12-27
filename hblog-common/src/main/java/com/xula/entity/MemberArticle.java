package com.xula.entity;

import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 用户收藏文章表
 * 
 * @author XXXX
 * @email xxx@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MemberArticle extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * uid
	 */
	private Integer id;
	/**
	 * 收藏用户uid
	 */
	private Integer uid;
	/**
	 * 文章编号
	 */
	private Integer articleId;
	/**
	 * 文章标题
	 */
	private String articleTitle;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;

}
