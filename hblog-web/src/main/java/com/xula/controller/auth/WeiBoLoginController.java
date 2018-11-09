package com.xula.controller.auth;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.GlobalConfig;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.LoginWayConstant;
import com.xula.base.constant.MemberConstant;
import com.xula.base.utils.*;
import com.xula.controller.WebController;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 微博登录 第三方
 */
@Controller
public class WeiBoLoginController extends BaseAuth {

    private final static String APP_ID = "882681036";

    private final static String APP_KEY = "5f8022f3b0456d240e088601e41d5b31";

    private final static String GET_CODE_URL = "https://api.weibo.com/oauth2/authorize";

    private final static String loginCallback = "http://www.xulian.net.cn:8088/authLoginCallback";

    private final static String reduceCallback = "http://www.xulian.net.cn:8088/authReduceCallback";

    private Logger logger = LoggerFactory.getLogger(WeiBoLoginController.class);

    /**
     * 微博 授权
     *
     * @return
     */
    @RequestMapping("/weiboLogin")

    public String index(HttpServletRequest request, HttpServletResponse response) {

        /**
         * 如果是用户已经登录了，对微博进行绑定我们是直接在存在的用户进行绑定
         */
        int uid = WebReqUtils.getSessionUid(request);
        String referrer = request.getParameter("referrer"); // 来路地址
        String fr = request.getParameter("fr"); // 渠道标识
        String userIp = WebReqUtils.getIp(request); // 登录请求ip

        // 临时存储用户发起数据
        JSONObject json = new JSONObject();
        json.put("referrer", referrer);
        json.put("fr", fr);
        json.put("userIp", userIp);
        json.put("authWay", LoginWayConstant.weibo.getWay());
        json.put("uid", uid);
        try {
            String state = Md5Utils.md5(json.toString());
            CookieUtil.setCookie(response, state, Base64Helper.encode(json.toString(), "utf-8"), 60 * 60 * 3);
            StringBuffer authUrl = new StringBuffer(GET_CODE_URL);
            authUrl.append("?client_id=" + APP_ID);
            authUrl.append("&redirect_uri=" + URLEncoder.encode(loginCallback, "utf-8"));
            authUrl.append("&response_type=code");
            authUrl.append("&state=" + state);
            return ("redirect:" + authUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 微博 授权
     *
     * @return
     */
    @RequestMapping("/authLoginCallback")
    public String authLoginCallback(HttpServletRequest request, Model model,HttpServletResponse response) {
        try {
            String state = request.getParameter("state");
            String code = request.getParameter("code");
            String userParamStr = CookieUtil.getCookie(request, state);
            // 解析参数
            JSONObject userParam = JSONObject.parseObject(Base64Helper.decode(userParamStr, "utf-8"));

            if (GlobalConfig.dev) {
                logger.info("微信授权确认-处理用户数据逻辑：state：" + state + ", code:" + code + ",param:" + userParam);
            }
            String referrer = userParam.getString("referrer");
            if (StringUtils.isBlank(referrer)) {
                referrer = "http://www.xulian.net.cn:8088/index";
            }
            String api = "https://api.weibo.com/oauth2/access_token";
            Map<String, String> data = new HashMap<String, String>();
            data.put("client_id", APP_ID);
            data.put("client_secret", APP_KEY);
            data.put("code", code);
            data.put("grant_type", "authorization_code");
            data.put("redirect_uri", loginCallback);
            String result = HttpUtil.httpPostByKeyValue(api, data, null);

            JSONObject tokenInfo = JSONObject.parseObject(result);
            result = getUserInfo(tokenInfo.getString("access_token"), tokenInfo.getString("uid"));

            JSONObject jsonObject = JSONObject.parseObject(result);
            /**
             * 获取微博的用户信息
             */
            String uuid = jsonObject.getString("id");

            RecordBean<Member> recordBean1 = boforeReg(uuid,MemberConstant.CHANNEL_BY_WEIBO);
            // 如果已经授权的用户直接登录
            if (recordBean1.isSuccessCode()) {
                request.getSession().setAttribute(GlobalConstant.SESSION_UID, recordBean1.getData().getUid());
                return ("redirect:" + referrer);
            }
            String nickname = jsonObject.getString("screen_name");
            String city = jsonObject.getString("city");
            String sex = jsonObject.getString("gender");
            String avatar = jsonObject.getString("avatar_large");
            String ip = ReqUtils.getIp(request);

            RecordBean<Member> recordBean = iMemberService.registered(nickname,uuid,city,avatar,sex,ip, MemberConstant.CHANNEL_BY_WEIBO);

            if (recordBean.isSuccessCode()) {
                gotoReg(request,response,ac,recordBean.getData(),LoginWayConstant.weibo.getWay());
            }
            return ("redirect:" + referrer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return renderTips(model,"授权失败");
    }


    /**
     * 微博 取消授权
     *
     * @return
     */
    @RequestMapping("/authReduceCallback")

    public String authReduceCallback(HttpServletRequest request) {
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        String api = "https://graph.qq.com/oauth2.0/token";
        Map<String, String> data = new HashMap<String, String>();
        data.put("client_id", APP_ID);
        data.put("client_secret", APP_KEY);
        data.put("code", code);
        data.put("grant_type", "authorization_code");
        data.put("redirect_uri", loginCallback);
        String result = null;
        try {
            result = HttpUtil.httpPostByKeyValue(api, data, null);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param uid
     * @return
     * @throws IOException
     */
    private String getUserInfo(String accessToken, String uid) throws IOException {
        String api = "https://api.weibo.com/2/users/show.json";
        Map<String, String> data = new HashMap<String, String>();
        data.put("access_token", accessToken);
        data.put("uid", uid);
        String result = HttpUtil.httpGet(api, data, null);
        return result;
    }
}
