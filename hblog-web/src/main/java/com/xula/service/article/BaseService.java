package com.xula.service.article;


import com.xula.base.utils.WebReqUtils;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;

/**
 * 基础服务
 * @author xla
 */
public class BaseService {



    @Autowired
    private HttpServletRequest request;


    /**
     * 获取用户uid
     * @return
     */
    public Integer getUid() {
        return WebReqUtils.getSessionUid(request);
    }
}
