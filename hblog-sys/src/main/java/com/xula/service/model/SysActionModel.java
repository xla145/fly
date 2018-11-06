package com.xula.service.model;


import com.xula.entity.SysAction;

/**
 * 系统功能
 * @author xla
 */
public class SysActionModel extends SysAction {

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
