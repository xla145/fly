package com.xula.controller.auth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.HttpUtil;
import com.xula.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 第三方qq授权登录
 *
 * @author xla
 * @date 20181025
 */
@Controller
public class QQLoginController {


    private final static String APP_ID = "101392599";

    private final static String APP_KEY = "47af19b47870932e2ba4d1008a705961";

    private final static String GET_CODE_URL = "https://graph.qq.com/oauth2.0/authorize";

    private final static String loginCallback = "http://www.xulian.net.cn:8088/afterlogin";

    static final String[] EMPTY_STRING_ARRAY = new String[0];

    @RequestMapping("/qqTest")
    public String qqTest() {
        return "login";
    }


    /**
     * qq 授权
     *
     * @return
     */
    @RequestMapping("/qqLogin")
    public String index() {
        try {
            StringBuffer authUrl = new StringBuffer(GET_CODE_URL);
            authUrl.append("?client_id=" + APP_ID);
            authUrl.append("&redirect_uri=" + URLEncoder.encode(loginCallback, "utf-8"));
            authUrl.append("&response_type=code");
            authUrl.append("&state=" + "TEST");
            return ("redirect:" + authUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * qq回调 applications over the unaudited use restrictions
     *
     * @return
     */
    @RequestMapping("/afterlogin")
    public String afterlogin(HttpServletRequest request) {
        // state 用户携带的信息
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
            result = HttpUtil.httpGet(api, data, null);
            Map<String, Object> map = stringToMap(result);
            if (result != null) {

                // 获取用户的openId
                result = getMemberInfo(map.get("access_token").toString());

                // 因为返回的值是 callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ); 需要获取括号里的值

                result = result.substring(result.indexOf("(") + 1, result.indexOf(")"));

                // 将 {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} 转成对象
                JSONObject jsonObject = JSONObject.parseObject(result);

                // 获取用户信息
                UserInfo userInfo = getUserInfo(map.get("access_token").toString(), jsonObject.get("openid").toString());

                System.out.println(JSON.toJSONString(userInfo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * grant_type=333&grant_type=666&grant_type=444 转成map形式
     *
     * @return
     */
    public Map<String, Object> stringToMap(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        if (!StringUtils.contains(content, "&") || !StringUtils.contains(content, "=")) {
            return null;
        }
        String[] st = content.split("&");
        Map<String, Object> map = new HashMap<>(st.length);
        for (String s : st) {
            String[] s1 = s.split("=");
            map.put(s1[0], s1[1]);
        }
        return map;
    }


    /**
     * 获取用户的openId
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    private String getMemberInfo(String accessToken) throws IOException {
        String api = "https://graph.qq.com/oauth2.0/me";
        Map<String, String> data = new HashMap<String, String>();
        data.put("access_token", accessToken);
        return HttpUtil.httpGet(api, data, null);
    }


    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     * @throws IOException
     */
    private UserInfo getUserInfo(String accessToken, String openId) throws IOException {
        String api = "https://graph.qq.com/user/get_user_info";

        Map<String, String> data = new HashMap<String, String>();
        data.put("access_token", accessToken);
        data.put("oauth_consumer_key", APP_ID);
        data.put("openid", openId);

        String result = HttpUtil.httpGet(api, data, null);

        JSONObject jsonObject = JSONObject.parseObject(result);

        UserInfo userInfo = JSONObject.parseObject(result, UserInfo.class);

        String figureurl = jsonObject.getString("figureurl_qq_1");

        userInfo.setAvatar(figureurl);

        return userInfo;
    }
}
