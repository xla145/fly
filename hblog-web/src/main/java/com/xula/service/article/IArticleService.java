package com.xula.service.article;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Article;
import com.xula.entity.extend.ArticleList;

import java.util.List;

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


    /**
     * 分页获取文章信息
     * @param conn
     * @param pageSize
     * @param pageNo
     * @return
     */
    PagePojo<ArticleList> getArticlePage(Conditions conn, Integer pageNo, Integer pageSize);

    /**
     * 获取文章列表
     * @param conn
     * @return
     */
    List<Article> getArticleList(Conditions conn);


    /**
     * 获取文章详情
     * @param aid
     * @return
     */
    Article getArticle(String aid);


    /**
     * 获取文章详情
     * @param aid
     * @return
     */
    RecordPojo getArticleInfo(String aid);


    /**
     * 获取用户的提问列表
     * @return
     */
    List<Article> getAskList(Integer uid);
}
