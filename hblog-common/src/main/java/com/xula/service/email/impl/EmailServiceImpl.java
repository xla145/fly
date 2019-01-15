package com.xula.service.email.impl;

import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.RecordBean;
import com.xula.service.email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送实现类
 * @author xla
 */
@Service("IEmailService")
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public RecordBean<String> send(String email, String tempId, JSONObject params) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        String title = params.getString("title");
        String content = params.getString("content");
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
        return RecordBean.success("");
    }
}
