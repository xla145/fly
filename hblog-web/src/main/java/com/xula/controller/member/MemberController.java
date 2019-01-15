package com.xula.controller.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.auth.Login;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.PageConstant;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.WebReqUtils;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.entity.MemberArticle;
import com.xula.entity.MemberMessage;
import com.xula.entity.extend.ArticleList;
import com.xula.entity.extend.SignList;
import com.xula.event.EventModel;
import com.xula.event.TaskEvent;
import com.xula.service.article.IArticleService;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import com.xula.service.oss.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户控制层
 * @author xla
 */
@Controller
public class MemberController extends WebController {

    @Autowired
    public  IMemberService iMemberService;
    @Autowired
    private IWebMemberService iWebMemberService;
    @Autowired
    private IArticleService iArticleService;

    /**
     * 跳转到用户详情
     * @return
     */
    @GetMapping(value = "/member/{uid}")
    public String index(@PathVariable Integer uid, Model model) {
        model.addAttribute("uid",uid);
        return PageConstant.USER_HOME;
    }


    /**
     * 跳转到用户详情
     * @return
     */
    @Login
    @PostMapping(value = "/member/sign")
    @ResponseBody
    public JSONObject sign() {
        Integer uid = WebReqUtils.getSessionUid(request);
        Member member = iMemberService.getMember(uid);
        //发布签到送积分事件
        EventModel eventModel = new EventModel();
        Map<String, Object> param = new HashMap<>(2);
        param.put("taskCode", "SignTask");
        eventModel.setParam(param);
        eventModel.setMember(member);
        ac.publishEvent(new TaskEvent(eventModel));
        return JsonBean.success("success");
    }



    /**
     * 判断用户签到状态
     * @return
     */
    @PostMapping(value = "/member/sign/status")
    @ResponseBody
    public JSONObject signStatus() {
        Integer uid = WebReqUtils.getSessionUid(request);
        RecordBean<RecordPojo> recordBean = iMemberService.checkSignStatus(uid);
        return JsonBean.success("success", recordBean.getData().getColumns());
    }



    /**
     * 用户签到列表显示
     * @return
     */
    @PostMapping(value = "/member/sign/show")
    @ResponseBody
    public JSONObject show() {
        List<List<SignList>> data = iWebMemberService.getSignedList();
        return JsonBean.success("success", data);
    }


    /**
     * 用户上传头像
     * @return
     */
    @PostMapping(value = "/member/upload")
    @ResponseBody
    public JSONObject uploadAvatar(@RequestParam("avatar") String avatar) {
        RecordBean<String> result = iWebMemberService.updateAvatar(avatar,WebReqUtils.getSessionUid(request));
        if (!result.isSuccessCode()) {
            return JsonBean.error("更新头像失败！");
        }
        return JsonBean.success("success");
    }

    /**
     * 修改用户密码
     * @return
     */
    @PostMapping(value = "/member/repass")
    @ResponseBody
    public JSONObject repass(@RequestParam("nowPwd") String nowPwd,@RequestParam("pwd") String pwd) {
        RecordBean<String> result = iWebMemberService.updatePwd(nowPwd,pwd,WebReqUtils.getSessionUid(request));
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }


    /**
     * 用户信息更新
     * @return
     */
    @PostMapping(value = "/member/update")
    @ResponseBody
    public JSONObject update(Member member) {
        member.setUid(WebReqUtils.getSessionUid(request));
        RecordBean<Member> result = iWebMemberService.updateMember(member);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }

    /**
     * 用户发布的文章
     * @return
     */
    @PostMapping(value = "/member/article")
    @ResponseBody
    public JSONObject article(@RequestParam(value = "page",required = false,defaultValue = "1") Integer pageNo) {
        Conditions conn = new Conditions("create_uid", SqlExpr.EQUAL,WebReqUtils.getSessionUid(request));
        PagePojo<ArticleList> page = iArticleService.getArticlePage(conn,pageNo, GlobalConstant.PAGE_SIZE);
        return JsonBean.page("success",page);
    }


    /**
     * 获取用户的消息
     * @return
     */
    @PostMapping(value = "/member/message/find")
    @ResponseBody
    public JSONObject find() {
        Conditions conn = new Conditions("to_uid", SqlExpr.EQUAL,WebReqUtils.getSessionUid(request));
        List<MemberMessage> list = iWebMemberService.getMemberMessageList(conn);
        return JsonBean.success("success",list);
    }


    /**
     * 删除用户的消息
     * @return
     */
    @PostMapping(value = "/member/message/remove")
    @ResponseBody
    public JSONObject remove(@RequestParam(value = "id",required = false) Integer id,@RequestParam(value = "all",required = false) boolean all) {
        RecordBean<String> result = iWebMemberService.delMemberMessage(id,WebReqUtils.getSessionUid(request),all);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }


    /**
     * 获取用户的最新消息
     * @return
     */
    @PostMapping(value = "/member/message/nums")
    @ResponseBody
    public JSONObject messageNums() {
        Conditions conn = new Conditions("to_uid", SqlExpr.EQUAL,WebReqUtils.getSessionUid(request));
        conn.add(new Conditions("status",SqlExpr.EQUAL,0), SqlJoin.AND);
        List<MemberMessage> list = iWebMemberService.getMemberMessageList(conn);
        return JsonBean.success("success",list);
    }


    /**
     * 删除用户的消息
     * @return
     */
    @PostMapping(value = "/member/message/read")
    @ResponseBody
    public JSONObject messageRead() {
        RecordBean<String> result = iWebMemberService.readMemberMessage(WebReqUtils.getSessionUid(request));
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }
}
