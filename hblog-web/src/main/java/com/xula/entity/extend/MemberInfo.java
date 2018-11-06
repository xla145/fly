package com.xula.entity.extend;

import com.xula.entity.Member;
import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 * @author xla
 */
@Data
public class MemberInfo extends Member {

    /**
     * uid
     */
    private Integer uid;
    /**
     * 会员当前vip等级
     */
    private Integer vip;
    /**
     * 会员当前vip等级名
     */
    private String vipName;
    /**
     * 会员当前成长值
     */
    private Long growthValue;
    /**
     * 会员当前积分
     */
    private Long pointValue;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
