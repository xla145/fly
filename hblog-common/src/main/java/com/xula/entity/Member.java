package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 * 
 * @author xla
 * @email xla@yuelinghui.com
 * @date 2018-11-02 17:19:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Member extends BasePojo {
    @Temporary
	private static final long serialVersionUID = 1L;

	/**
	 * uid
	 */
	@Id
	private Integer uid;
	/**
	 * 状态：0 无效， 1 有效， -1 删除
	 */
	private Integer status;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 性别 1：男 2：女
	 */
	private Integer sex;
	/**
	 * 注册渠道
	 */
	private String fr;
	/**
	 * 注册平台
	 */
	private String platform;
	/**
	 * 注册ip
	 */
	private String ip;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 最后登录时间
	 */
	private Date lastTime;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 会员生日
	 */
	private Date birthday;
	/**
	 * 注册渠道编号 默认1 自注册 2:qq 3:微博
	 */
	private Integer channelId;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 签名
	 */
	private String signature;
}
