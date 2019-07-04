package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.Category;
import com.xula.entity.Navigation;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.nav.INavigationService;

import java.util.List;
import java.util.Map;


/**
 * 获取导航栏列表
 * @author xla
 */
public class GetNavNodes extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

        Object checked =  params.get("checked");
        INavigationService iNavigationService = SpringFactory.getBean("INavigationService");
        List<Navigation> navigations = iNavigationService.getNavigationList();
        root.put("data", navigations);
        if (checked != null) {
            root.put("checked",checked);
        }
        return root;
    }
}
