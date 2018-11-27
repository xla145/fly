package com.xula.base.constant;


import org.springframework.beans.factory.annotation.Value;

/**
 * 常量类
 *
 * @author caibin
 */
public class BaseConstant {

    public static  boolean isDev = false;
    /**
     * 系统用户登录session标识
     */
    public static final String SYS_UID = "uid";
    public static final String SYS_USER = "sys_user";

    /**
     * 客户登录session标识
     */
    public static final String SYS_CID = "cid";
    public static final String SYS_CUSTOMERS = "sys_customers";

}
