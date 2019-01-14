package com.xula.controller.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.auth.Login;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.MemberArticleConstant;
import com.xula.base.constant.PageConstant;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.WebReqUtils;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.entity.MemberArticle;
import com.xula.entity.extend.ArticleList;
import com.xula.entity.extend.SignList;
import com.xula.event.EventModel;
import com.xula.event.TaskEvent;
import com.xula.service.article.IArticleService;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户控制层
 * @author xla
 */
@Controller
@RequestMapping("/member/article")
public class MemberArticleController extends WebController {


    @Autowired
    private IWebMemberService iWebMemberService;
    @Autowired
    private IArticleService iArticleService;

    /**
     * 用户收藏的文章
     * @return
     */
    @PostMapping(value = "/collectList")
    @ResponseBody
    public JSONObject collect(@RequestParam(value = "page",required = false,defaultValue = "1") Integer pageNo) {
        Conditions conn = new Conditions("uid", SqlExpr.EQUAL,WebReqUtils.getSessionUid(request));
        PagePojo<MemberArticle> page = iWebMemberService.getMemberArticlePage(conn,pageNo, GlobalConstant.PAGE_SIZE);
        return JsonBean.page("success",page);
    }


    /**
     * 判断用户是否收藏文章
     * @return
     */
    @RequestMapping(value = "/isCollect",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isCollect(HttpServletRequest request) {
        String aid = WebReqUtils.getParam(request,"aid",null);
        MemberArticle memberArticle = iArticleService.getMemberArticle(aid,WebReqUtils.getSessionUid(request));
        Map<String,Object> map = new HashMap<>();
        if (memberArticle == null) {
            map.put("collection",false);
        } else if (MemberArticleConstant.STATUS_COLECTED.equals(memberArticle.getStatus())){
            map.put("collection",true);
        }
        return JsonBean.success("success",map);
    }


    /**
     * 收藏文章
     * @return
     */
    @RequestMapping(value = "/collect",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject collect(@RequestParam("aid") String aid) {
        RecordBean<String> result = iArticleService.addMemberArticle(aid,WebReqUtils.getSessionUid(request));
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }


    /**
     * 取消收藏文章
     * @return
     */
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cancelCollect(@RequestParam("aid") String aid) {
        RecordBean<String> result = iArticleService.cancelMemberArticle(aid,WebReqUtils.getSessionUid(request));
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }
}

