package com.xula.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.ReqUtils;
import com.xula.base.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录
 * @author xla
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer producer;

    @RequestMapping(value = {"/login","/login.html"}, method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    /**
     * 系统用户登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sys/login", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject login(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!code.equalsIgnoreCase(kaptcha)) {
            return JsonBean.error("验证码不正确");
        }

        if (StringUtils.isBlank(userName)) {
            return JsonBean.error("请输入用户名");
        }
        if (StringUtils.isBlank(password)) {
            return JsonBean.error("请输入密码");
        }
        if (StringUtils.isBlank(code)) {
            return JsonBean.error("请输入验证码");
        }

        try {
            String ip = ReqUtils.getIp(request);
            ShiroUtils.setSessionAttribute("ip", ip);
            Subject subject = ShiroUtils.getSubject();
            //MD5加密
            password = CommonUtil.md5(password);
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);
        } catch (UnknownAccountException | LockedAccountException | IncorrectCredentialsException e) {
            return JsonBean.error(e.getMessage());
        } catch (AuthenticationException e) {
            return JsonBean.error("账户验证失败");
        }
        return JsonBean.success("登录成功");
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/sys/loginOut")
    @ResponseBody
    public JSONObject loginOut() {
        Integer uidObj = ShiroUtils.getUserId();
        logger.info("用户退出操作........ uid:" + uidObj);
        // 清除session
        ShiroUtils.logout();
        return JsonBean.success("退出成功！");
    }

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

}
