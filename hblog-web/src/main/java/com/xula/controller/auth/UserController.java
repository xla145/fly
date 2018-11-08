package com.xula.controller.auth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public JSONObject login(@RequestBody Member member) {
        RecordBean<String> recordBean = userValidation(member);
        if (!recordBean.isSuccessCode()) {
            return JsonBean.error(recordBean.getMsg());
        }
        String password = member.getPassword();
        String email = member.getEmail();
        RecordBean<Member> result = iMemberService.login(password,email);
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
    public JSONObject registered(@RequestBody Member member) {
        RecordBean<String> recordBean = userValidation(member);
        if (!recordBean.isSuccessCode()) {
            return JsonBean.error(recordBean.getMsg());
        }
        String password = member.getPassword();
        String email = member.getEmail();
        String nickname = member.getNickname();
        RecordBean result = iMemberService.registered(nickname,password,email);
        if (result.isSuccessCode()) {
            JsonBean.success("success",result.getData());
        }
        return JsonBean.error(result.getMsg());
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
}
