package com.xula.base.cas;

import com.xula.base.utils.CommonUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * rewrite redisSessionDAO class
 * @author xla
 */
public class ReRedisSessionDao extends RedisSessionDAO {

    @Override
    public void update(Session session) throws UnknownSessionException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        /**
         * 如果是静态资源请求则不需要更新session
         */
        System.out.println(request.getRequestURI());
        if (CommonUtil.isStaticRequest(request.getRequestURI())) {
            System.out.println(request.getRequestURI());
            return;
        }
        super.update(session);
    }


}
