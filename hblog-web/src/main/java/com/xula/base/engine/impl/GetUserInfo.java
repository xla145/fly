package com.xula.base.engine.impl;

import com.xula.base.engine.ModuleData;
import com.xula.base.helper.SpringFactory;
import com.xula.base.utils.WebReqUtils;
import com.xula.entity.extend.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 判断用户是否登录，返回用户信息
 * @author xla
 */
public class GetUserInfo extends ModuleData {

    @Override
    public Map<String, Object> getModelData(Map<String, Object> params) throws Exception {

        int uid = WebReqUtils.getSessionUid(getRequest());
        if (uid == -1) {
            root.put("data", null);
            return root;
        }
        MemberInfo member = new MemberInfo();

        member.setAvatar("https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg");
        member.setNickname("贤心");
        member.setVipName("vip会员");
        root.put("data", member);
        return root;
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
}
