package com.xula.entity.extend;

import com.xula.entity.Article;
import lombok.Data;

/**
 * 文章列表
 */
@Data
public class ArticleList extends Article {


    /**
     * 用户uid
     */
    private  Integer uid;

    /**
     * 头像
     */
    private  String avatar;

    /**
     * 昵称
     */
    private  String nickname;

    /**
     * 文章链接
     */
    private  String url;

    /**
     * 评论数
     */
    private  Integer commentNum;

    /**
     * vip名称
     */
    private String vipName;

}
