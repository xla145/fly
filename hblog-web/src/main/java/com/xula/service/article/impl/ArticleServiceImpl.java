package com.xula.service.article.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Article;
import com.xula.entity.Category;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.BaseService;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.article.IArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 文章管理
 * @author xla
 */
@Service("iArticleService")
@CacheConfig(cacheNames = "cache-time-30")
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


    /**
     * 分页获取文章信息
     * @param conn
     * @param pageSize
     * @param pageNo
     * @return
     */
    @Cacheable(keyGenerator = "customKeyGenerator")
    @Override
    public PagePojo<ArticleList> getArticlePage(Conditions conn, Integer pageNo, Integer pageSize) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.*,m.uid,m.avatar,m.nickname,mi.vip_name ");
        sql.append("FROM article a JOIN member m ON(a.create_uid = m.uid) LEFT JOIN member_info mi ON(m.uid = mi.uid) ");
        sql.append("WHERE 1=1 ");
        List<Object> params = null;
        if (conn != null && StringUtils.isNotEmpty(conn.getConnSql())) {
            sql.append("AND "+ conn.getConnSql());
            params = conn.getConnParams();
        }
        Sort sort = new Sort("a.create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(ArticleList.class,sql.toString(),params,sort,pageNo,pageSize);
    }


    /**
     *
     * @return
     */
    @Cacheable
    @Override
    public List<Article> getArticleList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Article.class,conn);
    }
}
