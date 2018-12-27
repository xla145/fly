package com.xula.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 下拉框展示的类
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Options {

    private Integer id;
    private String name;
}
