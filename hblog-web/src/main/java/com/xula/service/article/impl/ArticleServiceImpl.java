package com.xula.service.article.impl;

import cn.assist.easydao.dao.BaseDao;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Article;
import com.xula.entity.Category;
import com.xula.service.article.BaseService;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.article.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 文章管理
 * @author xla
 */
@Service
public class ArticleServiceImpl extends BaseService implements IArticleService {

    @Autowired
    private IArticleCategoryService iArticleCategoryService;

    /**
     * 添加文章信息
     * @param catId
     * @param title
     * @param info
     * @param payPoint
     * @return
     */
    @Override
    public RecordBean<Article> add(Integer catId, String title, String info, Integer payPoint) {
        Date now = new Date();
        String aid = CommonUtil.getId("ATC");
        Article article = new Article();
        article.setAid(aid);
        article.setCatId(catId);
        Category category = iArticleCategoryService.getCategory(catId);
        article.setCatName(category.getName());
        article.setCreateTime(now);
        article.setUpdateTime(now);
        article.setCreateUid(getUid());
        article.setInfo(info);
        article.setTitle(title);
        article.setPayPoint(payPoint);
        int result = BaseDao.dao.insert(article);
        if (result == 0) {
            return RecordBean.error("添加失败！");
        }
        return RecordBean.success("添加成功！");
    }

    @Override
    public RecordBean<Article> update(String articleId, String title, String info) {
        return null;
    }
}
