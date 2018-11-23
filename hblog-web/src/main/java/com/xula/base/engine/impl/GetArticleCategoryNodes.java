package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.Category;
import com.xula.entity.Options;
import com.xula.service.article.IArticleCategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 获取文章分类列表
 */
public class GetArticleCategoryNodes extends ModuleData {


    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {
        IArticleCategoryService iArticleCategoryService = SpringFactory.getBean("IArticleCategoryService");
        Object checked =  params.get("checked");
        List<Options> list = new ArrayList<Options>();
        List<Category> categoryList = iArticleCategoryService.getCategoryList();
        for (Category c:categoryList) {
            Options options = new Options();
            options.setId(c.getId());
            options.setName(c.getName());
            list.add(options);
        }
        if (checked != null) {
            root.put("checked",checked);
        }
        root.put("data", list);
        return root;
    }
}
