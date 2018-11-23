package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.entity.extend.HotArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xla
 */
public class GetHotArticleNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        List<HotArticle> list = new ArrayList<HotArticle>();
        for (int i = 0; i < 6; i++) {
            HotArticle hot = new HotArticle();
            hot.setUrl("article/detail.html");
            hot.setTitle("基于 layui 的极简社区页面模版");
            hot.setMessageNum(20);
            list.add(hot);
        }
        root.put("data", list);
        return root;
    }
}
