package com.xula.entity;


import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;

import java.util.Date;

/**
 * 书签分类
 * @author xla
 */
@Data
public class BookmarkCategory extends BasePojo {

    @Id
    private Integer id;

    /**
     * 名称
     */
    private String name;
    /**
     * code
     */
    private String code;
    /**
     * 状态 1：启用 0：停用
     */
    private Integer status;
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
