package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.Adv;
import com.xula.entity.Article;
import com.xula.entity.extend.MemberDetail;
import com.xula.service.article.IArticleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
@CacheConfig(cacheNames = "serviceCache-30")
public class GetAskList extends ModuleData {

    private IArticleService iArticleService = SpringFactory.getBean("iArticleService");

    @Cacheable
    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        Object uid = params.get("uid");
        if (uid == null) {
            return root;
        }
        List<Article> articleList = iArticleService.getAskList(Integer.valueOf(uid.toString()));
        root.put("data", articleList);
        return root;
    }
}
