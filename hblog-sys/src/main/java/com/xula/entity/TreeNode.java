package com.xula.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限树
 *
 * @author caibin
 */
public class TreeNode {

    private Integer id;
    private String name;
    private String checkboxValue;
    private TreeData data;
    private boolean spread = false;
    private boolean checked;
    private Integer parentId;
    private Integer roleId;
    private List<TreeNode> children = new ArrayList<TreeNode>();


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

    public String getCheckboxValue() {
        return checkboxValue;
    }

    public void setCheckboxValue(String checkboxValue) {
        this.checkboxValue = checkboxValue;
    }

    public TreeData getData() {
        return data;
    }

    public void setData(TreeData data) {
        this.data = data;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
