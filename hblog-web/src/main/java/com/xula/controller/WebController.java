package com.xula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * web项目的基础Controller
 * @author xla
 */
public class WebController extends BaseController{

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected ApplicationContext ac;

    private Integer pageNo = 1;


    /**
     * 对page进行封装
     * @param pageNo
     * @return
     */
    public Integer getPageNo(Integer pageNo) {
        if (pageNo == null) {
            return this.pageNo;
        }
        return pageNo;
    }
}
