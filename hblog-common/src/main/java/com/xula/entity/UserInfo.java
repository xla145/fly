package com.xula.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * qq 用户实体类
 *
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfo {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 出生年
     */
    private String year;

    /**
     * 用户头像
     */
    private String Avatar;
}
