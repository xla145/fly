package com.xula.service.article;

import com.xula.base.utils.RecordBean;
import com.xula.entity.Article;

/**
 * 文章管理
 * @author xla
 */
public interface IArticleService {


    /**
     * 添加文章信息
     * @param catId
     * @param title
     * @param info
     * @param payPoint
     * @return
     */
    RecordBean<Article> add(Integer catId,String title,String info,Integer payPoint);


    /**
     * 修改文章信息
     * @param aid
     * @param title
     * @param info
     * @return
     */
    RecordBean<Article> update(String aid,String title,String info);
}
