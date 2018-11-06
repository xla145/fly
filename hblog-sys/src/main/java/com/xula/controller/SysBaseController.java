package com.xula.controller;

import com.xula.base.helper.CustomBigDecimalEditor;
import com.xula.base.helper.SpecialDateEditor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 后台管理基础Controller
 */
public class SysBaseController extends BaseController {

    @Value("${online.domain}")
    private String domain;

    /**
     * 处理绑定对象参数中dete 转换问题
     *
     * @param dataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new SpecialDateEditor());
        dataBinder.registerCustomEditor(BigDecimal.class, new CustomBigDecimalEditor());
    }

    /**
     * 设置域名
     * @param model
     */
    public void setDomain(Model model) {
        model.addAttribute("domain", domain);
    }
}
