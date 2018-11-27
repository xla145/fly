package com.xula.controller;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.utils.JsonBean;
import com.xula.dao.one.MemberMapper;
import com.xula.dao.two.UserMapper;
import com.xula.entity.Article;
import com.xula.entity.Link;
import com.xula.entity.Member;
import com.xula.entity.User;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页信息
 * @author xla
 */
@Controller
public class IndexController extends WebController{

    private static List<Link> linkList = new ArrayList<>();

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IArticleService iArticleService;


    @RequestMapping(value = {"/","index"})
    public String index(Model model) {
        // 获取 filter 列表数据
        Map<String,Object> map = new HashMap<>();
//        map.put("column","");
//        map.put("filter","");
        PagePojo<ArticleList> page = iArticleService.getArticlePage(new Conditions(),1,15);
        model.addAttribute("page",page);
        model.addAttribute("data",map);
        return "index";
    }

    @RequestMapping(value = "loginTest")
    @ResponseBody
    public JSONObject loginTest(HttpServletRequest request) {

        Member member = memberMapper.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(member));

        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(user));

        request.getSession().setAttribute(GlobalConstant.SESSION_UID,1);
        return JsonBean.success("success",1);
    }



}
