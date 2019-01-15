package com.xula.controller.auth;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.LoginWayConstant;
import com.xula.base.constant.MemberConstant;
import com.xula.base.utils.*;
import com.xula.entity.Member;
import com.xula.entity.UserInfo;
import com.xula.service.dict.IDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 第三方qq授权登录
 *
 * @author xla
 * @date 20181025
 */
@RequestMapping("/qq")
@Controller
public class QQLoginController extends BaseAuth{


    private final static String APP_ID = "101534755";

    private final static String APP_KEY = "bd13818f9cc11ff04261e5bf2222e4ce";

    private final static String GET_CODE_URL = "https://graph.qq.com/oauth2.0/authorize";

    private final static String loginCallback = "http://test.xulian.net.cn/qq/authLoginCallback";

    @Autowired
    private IDictService iDictService;

    @Value("is.dev")
    private String isDev;

    /**
     * qq 授权
     *
     * @return
     */
    @RequestMapping("/login")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 如果是用户已经登录了，对微博进行绑定我们是直接在存在的用户进行绑定
         */
        int uid = WebReqUtils.getSessionUid(request);
        String fr = request.getParameter("fr"); // 渠道标识
        String userIp = WebReqUtils.getIp(request); // 登录请求ip

        // 临时存储用户发起数据
        JSONObject json = new JSONObject();
        json.put("fr", fr);
        json.put("userIp", userIp);
        json.put("authWay", LoginWayConstant.qq.getWay());
        json.put("uid", uid);
        json.put("referrer",request.getHeader("referrer"));
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
     * qq回调 applications over the unaudited use restrictions
     *
     * @return
     */
    @RequestMapping("/authLoginCallback")
    public String afterlogin(HttpServletRequest request, Model model, HttpServletResponse response) {
        try {// state 用户携带的信息
            String state = request.getParameter("state");
            String code = request.getParameter("code");
            String userParamStr = CookieUtil.getCookie(request, state);
            // 解析参数
            JSONObject userParam = JSONObject.parseObject(Base64Helper.decode(userParamStr, "utf-8"));
            Map<String,String> config = iDictService.getMapValue("QQ_LOGIN_CONFIG","CONFIG_CONTENT",",");
            if (stringToBoolean(isDev)) {
                logger.info("微信授权确认-处理用户数据逻辑：state：" + state + ", code:" + code + ",param:" + userParam);
            }
            String referrer = userParam.getString("referrer");
            if (StringUtils.isBlank(referrer)) {
                referrer = "http://test.xulian.net.cn/index";
            }
            String api = "https://graph.qq.com/oauth2.0/token";
            Map<String, String> data = new HashMap<String, String>();
            data.put("client_id", config.get("appId"));
            data.put("client_secret", config.get("appKey"));
            data.put("code", code);
            data.put("grant_type", "authorization_code");
            data.put("redirect_uri", config.get("loginCallBack"));
            String result = null;

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
                String openId = jsonObject.get("openid").toString();
                String ip = WebReqUtils.getIp(request);
                RecordBean<Member> recordBean1 = boforeReg(openId,MemberConstant.CHANNEL_BY_QQ);
                // 如果已经授权的用户直接登录
                if (recordBean1.isSuccessCode()) {
                    request.getSession().setAttribute(GlobalConstant.SESSION_UID, recordBean1.getData().getUid());
                    return ("redirect:" + referrer);
                }
                RecordBean<Member> recordBean = iMemberService.registered(userInfo.getNickname(),openId,userInfo.getCity(),userInfo.getAvatar(),userInfo.getGender(),ip, MemberConstant.CHANNEL_BY_QQ);
                if (recordBean.isSuccessCode()) {
                    gotoReg(request,response,ac,recordBean.getData(), LoginWayConstant.qq.getWay());
                }
            }
            return ("redirect:" + referrer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return renderTips(model,"授权失败");
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

    /**
     * 字符串 true，false 转 boolean
     * @param t
     * @return
     */
    public static boolean stringToBoolean(String t) {
        if (t.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }
}
