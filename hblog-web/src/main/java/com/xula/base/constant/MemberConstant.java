package com.xula.base.constant;

/**
 * 用户相关常量
 * @author xla
 */
public class MemberConstant {

    /**
     * 用户自注册
     **/
    public static final int CHANNEL_SELF  = 1;

    /**
     * 用户通过qq 注册
     **/
    public static final int CHANNEL_BY_QQ = 2;

    /**
     * 用户通过微博 注册
     **/
    public static final int CHANNEL_BY_WEIBO = 3;


    /**
     * 已绑定
     **/
    public static final int STATUS_BINDED = 3;

    /**
     * 未绑定
     **/
    public static final int STATUS_UNBINDING = 2;

    /**
     * 用户有效
     **/
    public static final int USET_DALID_YES = 5;


    /**************************************************************************/
    /**成长记录-增加*/
    public static final Integer MEMBER_GROW_LOG_SYMBOL_INCREASE = 1;
    /**成长记录-减少*/
    public static final Integer MEMBER_GROW_LOG_SYMBOL_DECREASE = 2;

    /**默认值为0*/
    public static final Integer ZERO = 0;
}
