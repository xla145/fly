package com.xula.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关常量
 * @author xla
 */
public class MemberConstant {

    public static Map<Integer,String> channelWay = new HashMap<>();

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
     * 邮箱未激活
     **/
    public static final int USET_EMAIL_UNACTIVE = 2;

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


    static {
        channelWay.put(CHANNEL_SELF,"网站注册");
        channelWay.put(CHANNEL_BY_QQ,"qq授权注册");
        channelWay.put(CHANNEL_BY_WEIBO,"微博授权注册");
    }
}
