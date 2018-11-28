package com.xula.service.article;


import cn.assist.easydao.common.Conditions;
import com.xula.entity.Article;
import com.xula.entity.Category;

import java.util.List;

/**
 * 文章分类管理
 */
public interface IArticleCategoryService {


    /**
     * 获取所有文章分类
     * @return
     */
    List<Category> getCategoryList();


    /**
     * 获取文章分类
     * @param catId
     * @return
     */
    Category getCategory(Integer catId);


    /**
     * 通过别名获取分类信息（别名唯一）
     * @param alias
     * @return
     */
    Category getCategoryByAlias(String alias);


    /**
     * 获取文章列表
     * @param conn
     * @return
     */
    List<Article> getArticleList(Conditions conn);
}
