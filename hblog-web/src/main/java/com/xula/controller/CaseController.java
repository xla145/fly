package com.xula.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 案例控制层
 * @author xla
 */
@RequestMapping("/case")
@Controller
public class CaseController extends WebController{


    /**
     * 案例首页跳转
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        return "/case/index";
    }


    /**
     * 案例列表
     * @return
     */
    @PostMapping(value = "/list")
    public JSONObject list() {



        return null;
    }


}
