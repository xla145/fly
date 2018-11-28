package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.Category;
import com.xula.service.article.IArticleCategoryService;

import java.util.List;
import java.util.Map;


/**
 * 获取文章分类列表
 */
public class GetCategoryNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

        Object checked =  params.get("checked");
        IArticleCategoryService iArticleCategoryService = SpringFactory.getBean("IArticleCategoryService");
        List<Category> categoryList = iArticleCategoryService.getCategoryList();
        root.put("data", categoryList);
        if (checked != null) {
            root.put("checked",checked);
        }
        return root;
    }
}
