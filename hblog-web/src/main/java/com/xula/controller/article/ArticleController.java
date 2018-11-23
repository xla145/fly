package com.xula.controller.article;

import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.WebReqUtils;
import com.xula.controller.WebController;
import com.xula.entity.Article;
import com.xula.service.article.IArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章的操作
 * @author xla
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends WebController {

    @Autowired
    private IArticleService iArticleService;

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
            return JsonBean.success(result.getMsg());
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
}
