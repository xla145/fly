package com.xula.service.email;

import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.RecordBean;

/**
 * 邮件发送类
 * @author xla
 */
public interface IEmailService {

    /**
     * 发送邮箱
     * @param email
     * @param tempId
     * @param params
     * @return
     */
    RecordBean<String> send(String email, String tempId, JSONObject params);
}
