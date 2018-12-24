package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

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
	/**
	 * 浏览文章用户
	 */
	private Integer uid;
	/**
	 * 文章编号
	 */
	private String articleId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 用户ip地址
	 */
	private String ip;
	/**
	 * 备注
	 */
	private String remark;
}
