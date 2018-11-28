package com.xula.base.engine.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.IArticleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 获取置顶的文章
 * @author xla
 */
public class GetArticleNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        IArticleService iArticleService = SpringFactory.getBean("iArticleService");
        PagePojo<ArticleList> page = iArticleService.getArticlePage(new Conditions("a.is_top", SqlExpr.EQUAL,1),1,5);
        root.put("data", page.getPageData());
        return root;
    }
}
