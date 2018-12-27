package com.xula.entity.extend;


import cn.assist.easydao.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 签到列表
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SignList extends BasePojo {

    /**
     * uid
     */
    private Integer uid;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 签到时间
     */
    private Date signTime;

    /**
     * 签到天数
     */
    private Integer days;
}
