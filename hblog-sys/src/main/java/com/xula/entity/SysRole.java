package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

/**
 * 系统角色
 *
 * @author caibin
 */
public class SysRole extends BasePojo {

    @Temporary
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;//id
    private String name;//角色名
    private String describe;//描述

    @Temporary
    private boolean isChecked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


}
