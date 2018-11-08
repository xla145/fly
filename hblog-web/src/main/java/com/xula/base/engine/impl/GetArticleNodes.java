package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.entity.extend.ArticleList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
public class GetArticleNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        List<ArticleList> list = new ArrayList<ArticleList>();
        for (int i = 0; i < 5; i++) {
            ArticleList articleList = new ArticleList();
            articleList.setUrl("user/home.html");
            articleList.setAvatar("https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
            articleList.setNickname("贤心");
            articleList.setCatName("公告");
            articleList.setTitle("基于 layui 的极简社区页面模版");
            articleList.setId(3);
            articleList.setVipName("vip会员");
            articleList.setPayPoint(30);
            articleList.setCommentNum(50);
            articleList.setCreateTime(new Date());
            articleList.setStatusName("已结");
            list.add(articleList);
        }
        root.put("data", list);
        return root;
    }
}
