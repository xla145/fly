package com.xula.entity.extend;

import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 * @author xla
 */
@Data
public class MemberDetail {

    /**
     * VIP 名称
     */
    private String vipName;

    /**
     * uid
     */
    private Integer uid;

    /**
     * vip
     */
    private Integer vip;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户积分
     */
    private Long pointValue;

    /**
     * 性别
     */
    private String sex;

    /**
     * 加入时间
     */
    private Date createTime;

    /**
     * 城市
     */
    private String city;

    /**
     * 签名
     */
    private String signature;


}
