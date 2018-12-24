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
}
