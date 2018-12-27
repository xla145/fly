package com.xula.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 链接类
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Link {

    /**
     * 链接名
     */
    private String name;

    /**
     * 链接地址
     */
    private String url;

}
