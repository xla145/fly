package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.Article;
import com.xula.entity.extend.CommentList;
import com.xula.service.article.IArticleService;
import com.xula.service.article.ICommentService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
@CacheConfig(cacheNames = "cache-time-30")
public class GetCommentList extends ModuleData {

    private ICommentService iCommentService = SpringFactory.getBean("ICommentService");

    @Cacheable
    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        Object uid = params.get("uid");
        if (uid == null) {
            return root;
        }
        List<CommentList> commentList = iCommentService.getCommentList(Integer.valueOf(uid.toString()));
        root.put("data", commentList);
        return root;
    }
}
