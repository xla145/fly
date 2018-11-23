package com.xula.service.article;


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
}
