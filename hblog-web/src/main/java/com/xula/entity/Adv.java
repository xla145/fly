package com.xula.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告model
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Adv {

    /**
     * 广告编号
     */
    private Integer id;

    /**
     * 广告的名称
     */
    private String name;

    /**
     * 广告的url
     */
    private String url;

    /**
     * 限制时间
     */
    private String timeLimit;
}
