package com.xula.service.article.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.Article;
import com.xula.entity.Category;
import com.xula.service.article.IArticleCategoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章分类管理
 * @author xla
 */
@Service("IArticleCategoryService")
@CacheConfig(cacheNames = "serviceCache-30")
public class ArticleCategoryServiceImpl implements IArticleCategoryService {


    /**
     * 获取文章分类列表
     * @return
     */
    @Cacheable
    @Override
    public List<Category> getCategoryList() {
        return BaseDao.dao.queryForListEntity(Category.class,new Conditions());
    }


    /**
     * 获取文章分类信息
     * @param catId
     * @return
     */
    @Override
    public Category getCategory(Integer catId) {
        return BaseDao.dao.queryForEntity(Category.class,catId);
    }


    /**
     *
     * @param alias
     * @return
     */
    @Cacheable(cacheNames = "serviceCache-30")
    @Override
    public Category getCategoryByAlias(String alias) {
        return BaseDao.dao.queryForEntity(Category.class,new Conditions("alias", SqlExpr.EQUAL,alias));
    }

    /**
     *
     * @return
     */
    @Cacheable(keyGenerator = "customKeyGenerator")
    @Override
    public List<Article> getArticleList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Article.class,conn);
    }
}
