package com.xula.service.model;


import com.xula.entity.SysUserRole;

/**
 * @author chenxx
 * @date 2018/6/27 0027 10:37
 */
public class SysUserRoleModel extends SysUserRole {

    private String name;//用户名

    private Integer num;//此角色的个数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
