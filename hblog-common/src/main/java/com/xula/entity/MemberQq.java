package com.xula.entity;


import cn.assist.easydao.annotation.Table;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户微博
 * @author xla
 */
@Table(name = "member_qq")
@Data
@EqualsAndHashCode(callSuper=false)
public class MemberQq extends BasePojo {

    /**
     * 网站uid
     */
    private int uid;

    /**
     * qq用户的uid
     */
    private String uuid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 授权状态 1：绑定中 2：解绑
     */
    private Integer status;
}
