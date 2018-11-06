package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.util.Date;

/**
 * 系统角色功能
 *
 * @author caibin
 */
public class SysAction extends BasePojo {

    @Temporary
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;//id
    private String name;//功能名称
    private String url;//功能url
    private Integer type;//1：系统功能 2：导航菜单
    private Integer parentId;//父级菜单id
    private String parentName;//父级菜单name
    private String remark;//备注
    private String icon;//图标
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Integer status;//状态
    private Integer weight;//权重
    private String perms; // 授权(多个用逗号分隔，如：user:list,user:create)
    private Integer level; // 等级


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 系统用户
     *
     * @author caibin
     */
    public static class SysUser extends BasePojo {
        @Temporary
        private static final long serialVersionUID = 1L;

        @Id
        private Integer uid;//用户uid
        private String name;//用户名
        private String pswd;//用户密码
        private String realName;//真实姓名
        private String mobile;//手机号
        private String qq;//联系qq
        private String email;//联系邮箱
        private Integer createUid;//创建人
        private Integer type; //类型 1:根用户 2：商户
        private Integer isValid;// 是有有效 0 无效 1 有效
        private Date createTime;//创建时间
        private Date updateTime;//更新时间
        private Date lastLoginTime;//最后登录时间
        private String remark;//备注
        private String sysCode;
        private String delFlag;


        public String getSysCode() {
            return sysCode;
        }

        public void setSysCode(String sysCode) {
            this.sysCode = sysCode;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPswd() {
            return pswd;
        }

        public void setPswd(String pswd) {
            this.pswd = pswd;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getCreateUid() {
            return createUid;
        }

        public void setCreateUid(Integer createUid) {
            this.createUid = createUid;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getIsValid() {
            return isValid;
        }

        public void setIsValid(Integer isValid) {
            this.isValid = isValid;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public Date getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(Date lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

    }


}
