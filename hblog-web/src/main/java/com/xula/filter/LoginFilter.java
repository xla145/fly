package com.xula.filter;

import com.xula.base.constant.PageConstant;
import com.xula.base.utils.WebReqUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录过滤器
 * @author xla
 */
@WebFilter(urlPatterns = {"/article/add.html,/article/edit"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {


    /**
     * 初始化
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    /**
     * 执行是否登录判断
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        int uid = WebReqUtils.getSessionUid(request);
        if (uid == -1) {
            response.sendRedirect(PageConstant.LOGIN);
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}