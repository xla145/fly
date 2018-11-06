package com.xula.controller.auth;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 微博登录 第三方
 */
@Controller
public class WeiBoLoginController {

    private final static String APP_ID = "882681036";

    private final static String APP_KEY = "5f8022f3b0456d240e088601e41d5b31";

    private final static String GET_CODE_URL = "https://api.weibo.com/oauth2/authorize";

    private final static String loginCallback = "http://www.xulian.net.cn:8088/authLoginCallback";


    private final static String reduceCallback = "http://www.xulian.net.cn:8088/authReduceCallback";

    /**
     * 微博 授权
     *
     * @return
     */
    @RequestMapping("/weiboLogin")

    public String index() {
        try {
            StringBuffer authUrl = new StringBuffer(GET_CODE_URL);
            authUrl.append("?client_id=" + APP_ID);
            authUrl.append("&redirect_uri=" + URLEncoder.encode(loginCallback, "utf-8"));
            authUrl.append("&response_type=code");
            authUrl.append("&state=" + "Test");
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

    public String authLoginCallback(HttpServletRequest request) {
        try {
            String state = request.getParameter("state");
            String code = request.getParameter("code");
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
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
