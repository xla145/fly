/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xula.base.utils;


import com.xula.base.utils.JsonBean;
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
