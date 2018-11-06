
package com.xula.entity;

import cn.assist.easydao.annotation.Id;
import cn.assist.easydao.annotation.Temporary;
import cn.assist.easydao.pojo.BasePojo;

import java.util.Date;

/**
 * 系统人员操作日志
 * @author xla
 */
public class SysOperatorLog extends BasePojo {
    @Temporary
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Integer sysUid;
    private String sysName;
    private String operation; // 操作
    private String method; // 方法名
    private String ip;
    private String params; // 参数
    private Date createTime;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysUid() {
        return sysUid;
    }

    public void setSysUid(Integer sysUid) {
        this.sysUid = sysUid;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}

