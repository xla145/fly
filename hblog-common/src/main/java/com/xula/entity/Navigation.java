package com.xula.entity;

import lombok.Data;

import java.util.Date;

/**
 * 导航栏
 * @author xla
 */
@Data
public class Navigation {

    private Integer id;

    private String name;

    private String url;

    private Integer weight;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String remark;
}