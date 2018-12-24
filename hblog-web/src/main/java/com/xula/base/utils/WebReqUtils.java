package com.xula.base.utils;

import com.xula.base.constant.GlobalConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * 请求的工具类
 * @author xla
 */
public class WebReqUtils extends ReqUtils {

    public static int getSessionUid(HttpServletRequest request){
        int sessionUid = -1;
        Object uidObj = request.getSession().getAttribute(GlobalConstant.SESSION_UID);

        if(uidObj == null){
            return sessionUid;
        }
        sessionUid = (Integer)uidObj;
        return sessionUid;
    }

    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }


    /**
     * 获取用户的uid
     * @return 一般般
     */
    public static int getSessionUid() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getSessionUid(request);
    }


    public static void main(String[] args) {

    }
}
