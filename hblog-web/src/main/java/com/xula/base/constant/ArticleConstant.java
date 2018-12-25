package com.xula.base.constant;

/**
 * 文章的常量类
 * @author xla
 */
public class ArticleConstant {

    public static final String WONDERFUL = "wonderful";

    public static final String UNSOLVED = "unsolved";

    public static final String SOLVED = "solved";





    /**
     * 文章状态
     */
    public static final Integer STATUS_DEL = -1;

    /**
     * 待审核 （有些类型的文章不需要审核，直接是审核通过）
     */
    public static final Integer STATUS_UNADUIT = 0;

    /**
     * 审核通过，发布
     */
    public static final Integer STATUS_AUDITED= 5;

    /**
     * 完结
     */
    public static final Integer STATUS_SOLVED = 15;

    /**
     * 审核不通过
     */
    public static final Integer STATUS_UNPASS= 20;
}
