package com.xula.event;


import com.xula.entity.SysAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author caibin
 */
public class EventModel {

    private Map<String, Object> param;
    private SysAction.SysUser sysUser;
    private HttpServletRequest request;
    private HttpServletResponse response;


    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public SysAction.SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysAction.SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


}
