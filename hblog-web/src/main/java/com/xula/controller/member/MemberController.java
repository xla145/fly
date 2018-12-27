package com.xula.controller.member;

import cn.assist.easydao.pojo.RecordPojo;
import cn.assist.easydao.util.JsonKit;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.auth.Login;
import com.xula.base.constant.ImgCategory;
import com.xula.base.constant.PageConstant;
import com.xula.base.utils.ImgUtil;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.WebReqUtils;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.entity.extend.SignList;
import com.xula.event.EventModel;
import com.xula.event.RegisterEvent;
import com.xula.event.TaskEvent;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import com.xula.service.oss.IUploadFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public IMemberService iMemberService;
    @Autowired
    private IWebMemberService iWebMemberService;
    @Autowired
    private IUploadFileService iUploadFileService;
    /**
     * 跳转到用户详情
     * @return
     */
    @RequestMapping(value = "/member/{uid}",method = RequestMethod.GET)
    public String index(@PathVariable Integer uid, Model model) {
        model.addAttribute("uid",uid);
        return PageConstant.USER_HOME;
    }


    /**
     * 跳转到用户详情
     * @return
     */
    @Login
    @RequestMapping(value = "/member/sign",method = RequestMethod.POST)
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
    @RequestMapping(value = "/member/sign/status",method = RequestMethod.POST)
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
    @RequestMapping(value = "/member/sign/show",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject show() {
        List<List<SignList>> data = iWebMemberService.getSignedList();
        return JsonBean.success("success", data);
    }


    /**
     * 用户上传头像
     * @return
     */
    @RequestMapping(value = "/member/upload",method = RequestMethod.POST)
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
    @RequestMapping(value = "/member/repass",method = RequestMethod.POST)
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
    @RequestMapping(value = "/member/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(Member member) {
        member.setUid(WebReqUtils.getSessionUid(request));
        RecordBean<Member> result = iWebMemberService.updateMember(member);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }

}
