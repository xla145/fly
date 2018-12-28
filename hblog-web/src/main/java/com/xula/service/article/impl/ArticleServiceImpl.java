package com.xula.service.article.impl;

import cn.assist.easydao.annotation.DataSource;
import cn.assist.easydao.annotation.DataSourceConfig;
import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import cn.assist.easydao.util.PojoHelper;
import com.xula.base.constant.ArticleConstant;
import com.xula.base.constant.DataSourceConstant;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Article;
import com.xula.entity.ArticleComment;
import com.xula.entity.Category;
import com.xula.entity.extend.ArticleList;
import com.xula.entity.extend.MemberDetail;
import com.xula.service.article.BaseService;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.article.IArticleService;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 文章管理
 * @author xla eligible
 */
@Service("iArticleService")
@DataSourceConfig(name = DataSourceConstant.DATA_SOURCE_A)
@CacheConfig(cacheNames = "cache-time-30")
public class ArticleServiceImpl extends BaseService implements IArticleService {

    @Autowired
    private IArticleCategoryService iArticleCategoryService;
    @Autowired
    private IWebMemberService iWebMemberService;


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
        PagePojo<ArticleList> page = BaseDao.dao.queryForListPage(ArticleList.class,sql.toString(),params,sort,pageNo,pageSize);
        return page;
    }

    /**
     *
     * @return
     */
    @Cacheable
    @Override
    @DataSource
    public List<Article> getArticleList(Conditions conn) {
        return BaseDao.dao.queryForListEntity(Article.class,conn);
    }


    /**
     * 获取文章详情
     * @param aid
     * @return
     */
    @Override
    public Article getArticle(String aid) {
        return BaseDao.dao.queryForEntity(Article.class,aid);
    }


    /**
     * 获取文章详情信息
     * @param aid
     * @return
     */
    @Override
    public RecordPojo getArticleInfo(String aid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT aid,title,cat_name,comment_num,pay_point,info,create_uid,status,is_top,is_good,browse FROM article ");
        sql.append("WHERE aid = ?");
        RecordPojo recordPojo = BaseDao.dao.query(sql.toString(),aid);
        // 获取 用户信息
        Integer uid = recordPojo.getInt("createUid");
        MemberDetail memberDetail = iWebMemberService.getMemberDetail(uid);
        recordPojo.set("member",memberDetail);
        return recordPojo;
    }


    /**
     * 获取用户提问列表
     * @param uid
     * @return
     */
    @Override
    public List<Article> getAskList(Integer uid) {
        Conditions conn = new Conditions("create_uid",SqlExpr.EQUAL,uid);
        conn.add(new Conditions("cat_id",SqlExpr.EQUAL, ArticleConstant.ASK_TYPE), SqlJoin.AND);
        return BaseDao.dao.queryForListEntity(Article.class,conn);
    }
}
