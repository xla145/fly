
package com.xula.base.utils;


import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理器
 *
 * @author xla
 * @email
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) handler;
                if (ex instanceof AuthorizationException) {
                    if ("JSONObject".equalsIgnoreCase(method.getMethod().getReturnType().getSimpleName())) {
                        response.setCharacterEncoding("UTF-8");
                        response.setHeader("Content-type", "application/json;charset=UTF-8");
                        response.getWriter().write(JsonBean.error(JsonBean.ERR, "对不起，您没有权限").toString());
                    } else {
                        response.sendRedirect("/no_permission");
                    }
                }
            }
        } catch (IOException e) {
            logger.error("RRExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
