package com.xula.controller.auth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户处理
 * @author xla
 */
@Controller
@RequestMapping("/user")
public class UserController extends WebController {

    @Autowired
    private IMemberService iMemberService;
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
    public JSONObject login() {
        return null;
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
     * 注册
     * @return
     */
    @RequestMapping(value = "reg",method = RequestMethod.POST)
    public JSONObject registered(@RequestBody Member member) {

        String password = member.getPassword();
        String email = member.getEmail();
        String nickname = member.getNickname();

        RecordBean result = iMemberService.registered(nickname,password,email);
        if (result.isSuccessCode()) {
            JsonBean.success("success",result.getData());
        }
        return JsonBean.error(result.getMsg());
    }



}
