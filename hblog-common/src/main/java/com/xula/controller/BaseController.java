package com.xula.controller;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * base Controller
 * @author xla
 */

public class BaseController {


    /**
     * 上下文环境地址
     *
     * @param request
     * @return
     */
    public static String contextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    }

    /**
     * @author cy
     * @description 得到项目的根目录的绝对路径
     */
    public static String getPath(HttpServletRequest request){
        String path = request.getServletContext().getRealPath("/");
        path = path.replaceAll("\\\\", "/");
        return path;
    }

    /**
     * 返回消息页面
     *
     * @param msg 消息内容
     */
    public String renderTips(Model model, String msg) {
        model.addAttribute("msg", msg);
        return "common/notice";
    }
}
