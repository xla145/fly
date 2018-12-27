package com.xula.entity.extend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户详情
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
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

    /**
     * 是否绑定了qq
     */
    private boolean isQQ = false;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 判断是否绑定qq
     * @return
     */
    public boolean getIsQQ() {
        return isQQ;
    }

    /**
     * 是否绑定了微博
     */
    private boolean isWeiBo = false;


    /**
     * 判断是否绑定微博
     * @return
     */
    public boolean getIsWeiBo() {
        return isWeiBo;
    }
}
