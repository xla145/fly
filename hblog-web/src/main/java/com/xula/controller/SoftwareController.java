package com.xula.controller;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.constant.ArticleConstant;
import com.xula.base.constant.GlobalConstant;
import com.xula.entity.Category;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.article.IArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 软件下载
 * @author xla
 */
@RequestMapping("/software")
@Controller
public class SoftwareController extends WebController{


    @Autowired
    private IArticleService iArticleService;

    @Autowired
    private IArticleCategoryService iArticleCategoryService;

    /**
     * 首页
     * @return
     */
    @GetMapping(value = {"","index"})
    public String index(Model model) {
        // 获取 filter 列表数据
        Map<String,Object> map = new HashMap<>();
        PagePojo<ArticleList> page = iArticleService.getArticlePage(null,1,GlobalConstant.PAGE_SIZE);
        map.put("type","all");
        map.put("filter","");

        model.addAttribute("page",page);
        model.addAttribute("data",map);
        return "/software/index";
    }


    /**
     * 获取软件列表
     * @return
     */
    @GetMapping(value = {"/list/","/list/{statusName}","/list/{statusName}/page/{pageNo}",})
    public String index(@PathVariable(required = false) String statusName, @PathVariable(required = false) Integer pageNo, Model model) {
        // 获取 filter 列表数据
        Map<String,Object> map = new HashMap<>(2);
        map.put("filter",statusName);
        map.put("type","software");
        PagePojo<ArticleList> page = iArticleService.getArticlePage(null,getPageNo(pageNo), GlobalConstant.PAGE_SIZE);

        model.addAttribute("page",page);
        model.addAttribute("data",map);
        return "/software/index";
    }
}
