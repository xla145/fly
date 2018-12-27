package com.xula.entity.extend;

import com.xula.entity.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章列表
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
     * vip名称
     */
    private String vipName;

}
