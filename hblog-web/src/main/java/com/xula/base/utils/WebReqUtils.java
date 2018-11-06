package com.xula.base.utils;

import com.xula.base.constant.GlobalConstant;

import javax.servlet.http.HttpServletRequest;

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
}
