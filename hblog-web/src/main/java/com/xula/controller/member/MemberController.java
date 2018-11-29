package com.xula.controller.member;

import com.xula.base.constant.PageConstant;
import com.xula.controller.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 用户控制层
 * @author xla
 */
@Controller
public class MemberController extends WebController {


    /**
     * 跳转到用户详情
     * @return
     */
    @RequestMapping(value = "/member/{uid}",method = RequestMethod.GET)
    public String index(@PathVariable Integer uid, Model model) {
        model.addAttribute("uid",uid);
        return PageConstant.USER_HOME;
    }

}
