package com.xula.controller.auth;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.LoginWayConstant;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户处理
 * @author xla
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseAuth {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * 跳转到登录页
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String index() {
        return "/user/login";
    }


    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody Member member,HttpServletRequest request,HttpServletResponse response) {
        RecordBean<String> recordBean = userValidation(member);
        if (!recordBean.isSuccessCode()) {
            return JsonBean.error(recordBean.getMsg());
        }
        String password = member.getPassword();
        String email = member.getEmail();
        RecordBean<Member> result = iMemberService.login(password,email);
        gotoLogin(request,response,ac,result.getData(), LoginWayConstant.email.getWay());
        if (result.isSuccessCode()) {
            return JsonBean.success("success!");
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 跳转到注册页
     * @return
     */
    @RequestMapping(value = "reg",method = RequestMethod.GET)
    public String reg() {
        return "/user/reg";
    }

    /**
     * 注册  验证
     * @return
     */
    @RequestMapping(value = "reg",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject registered(@RequestBody Member member,HttpServletRequest request,HttpServletResponse response) {
        RecordBean<String> recordBean = userValidation(member);
        if (!recordBean.isSuccessCode()) {
            return JsonBean.error(recordBean.getMsg());
        }
        String password = member.getPassword();
        String email = member.getEmail();
        String nickname = member.getNickname();
        RecordBean<Member> result = iMemberService.registered(nickname,password,email);

        gotoReg(request,response,ac,result.getData(), LoginWayConstant.email.getWay());
        if (result.isSuccessCode()) {
            JsonBean.success("success",result.getData());
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 跳转到基础信息页
     * @return
     */
    @RequestMapping(value = "set",method = RequestMethod.GET)
    public String set() {
        return "/user/set";
    }

    /**
     * 跳转到用户主页
     * @return
     */
    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String home() {
        return "/user/home";
    }


    /**
     * 跳转到激活页
     * @return
     */
    @RequestMapping(value = "activate",method = RequestMethod.GET)
    public String activate() {
        return "/user/activate";
    }


    /**
     * 跳转到我的信息页
     * @return
     */
    @RequestMapping(value = "message",method = RequestMethod.GET)
    public String message() {
        return "/user/message";
    }


    /**
     * 跳转到用户
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String userIndex() {
        return "/user/index";
    }


    /**
     * 跳转到忘记密码页
     * @return
     */
    @RequestMapping(value = "forget",method = RequestMethod.GET)
    public String forget() {
        return "/user/forget";
    }

    /**
     * 用户验证
     * @param member
     * @return
     */
    private RecordBean<String> userValidation(Member member) {
        if (StringUtils.isEmpty(member.getEmail())) {
            return RecordBean.error("email不能为空！");
        }
        if (StringUtils.isEmpty(member.getPassword())) {
            return RecordBean.error("密码不能为空！");
        }
        return RecordBean.success("success！");
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(GlobalConstant.SESSION_UID);
        return "/index";
    }
}
