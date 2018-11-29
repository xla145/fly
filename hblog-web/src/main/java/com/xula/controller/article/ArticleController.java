package com.xula.controller.article;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.ArticleConstant;
import com.xula.base.constant.PageConstant;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.WebReqUtils;
import com.xula.controller.WebController;
import com.xula.entity.Article;
import com.xula.entity.Category;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.IArticleCategoryService;
import com.xula.service.article.IArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章的操作
 * @author xla
 */
@Controller
@RequestMapping({"/article","/column"})
public class ArticleController extends WebController {

    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private IArticleCategoryService iArticleCategoryService;

    /**
     * 添加文章
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(HttpServletRequest request) {
        Integer catId = WebReqUtils.getParamToInteger(request,"catId",null);
        String title = WebReqUtils.getParam(request,"title",null);
        String info = WebReqUtils.getParam(request,"info",null);
        Integer payPoint = WebReqUtils.getParamToInteger(request,"payPoint",null);

        if (catId == null) {
            return JsonBean.error("文章类型不能为空！");
        }
        if (StringUtils.isEmpty(title)) {
            return JsonBean.error("文章标题不能为空！");
        }
        if (StringUtils.isEmpty(info)) {
            return JsonBean.error("文章内容不能为空！");
        }
        if (payPoint == null) {
            return JsonBean.error("悬赏飞吻不能为空！");
        }
        RecordBean<Article> result = iArticleService.add(catId,title,info,payPoint);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg(), PageConstant.INDEX);
        }
        return JsonBean.error(result.getMsg());
    }


    /**
     * 跳转添加文章页面
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addView() {
        return "article/add";
    }










    /**
     * 获取到文章列表页
     * @return
     */
    @RequestMapping(value = {"/list/{typeName}","/list/{typeName}/{statusName}","/list/{typeName}/{statusName}/page/{pageNo}",},method = RequestMethod.GET)
    public String index(@PathVariable String typeName,@PathVariable(required = false) String statusName,@PathVariable(required = false) Integer pageNo,Model model) {
        // 获取 filter 列表数据
        Map<String,Object> map = new HashMap<>();
        map.put("filter",statusName);
        map.put("type",typeName);
        PagePojo<ArticleList> page = iArticleService.getArticlePage(getConditions(typeName,statusName),1,15);

        Category category = iArticleCategoryService.getCategoryByAlias(typeName);
        iArticleCategoryService.getArticleList(new Conditions("cat_id",SqlExpr.EQUAL,category.getId()));

        model.addAttribute("page",page);
        model.addAttribute("data",map);
        System.out.println(typeName);
        System.out.println(pageNo);
        return "/article/index";
    }





    /**
     * article/list/all
     * article/list/all/page/2
     * article/list/all/unsolved/page/2
     * article/list/all/solved
     * article/list/quiz
     *
     */


    /**
     * 根据typeName，获取筛选条件(目前先这样，后期有好方法再优化)
     * @param typeName
     * @return
     */
    private Conditions getConditions(String typeName,String statusName) {
        Conditions con ;
        if (typeName.equalsIgnoreCase("all")) {
            con = new Conditions();
        } else {
            Category category = iArticleCategoryService.getCategoryByAlias(typeName);
            con = new Conditions("a.cat_id",SqlExpr.EQUAL,category.getId());
        }
        if (StringUtils.isEmpty(statusName)) {
            return con;
        }
        if (statusName.equalsIgnoreCase(ArticleConstant.UNSOLVED)) {
            con.add(new Conditions("a.status", SqlExpr.EQUAL,ArticleConstant.STATUS_UNSOLVED), SqlJoin.AND);
        } else if (statusName.equalsIgnoreCase(ArticleConstant.SOLVED)) {
            con.add(new Conditions("a.status", SqlExpr.EQUAL,ArticleConstant.STATUS_SOLVED),SqlJoin.AND);
        } else {
            con.add(new Conditions("a.is_good", SqlExpr.EQUAL,1),SqlJoin.AND);
        }
        return con;
    }
}
