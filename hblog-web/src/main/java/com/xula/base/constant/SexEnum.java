package com.xula.base.constant;

/**
 * 性别的枚举类型
 * @author xla
 */
public enum  SexEnum {

    /**
     * 男
     */
    MALE(1,"男"),
    /**
     * 女
     */
    FEMALE(2,"女");


    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 构造函数
     * @param type
     * @param name
     */
    SexEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 获取性别名称
     * @param type
     * @return
     */
    public static String getNameById(Integer type) {
        for (SexEnum s:SexEnum.values()) {
            if (s.getType().equals(type)) {
                return s.getName();
            }
        }
        return SexEnum.MALE.getName();
    }
}
