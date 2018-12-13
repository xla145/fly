package com.xula.base.utils;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * response json格式
 *
 * @author caibin
 */
public class JsonBean {

    public static final int ERR = -1;
    public static final int OK = 0;

    public static JSONObject error(String msg) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.ERR);
        json.put("msg", msg);
        return json;
    }

    public static JSONObject error(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    public static JSONObject error(int code, String msg, String data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        return json;
    }

    public static JSONObject success(String msg) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.OK);
        json.put("msg", msg);
        return json;
    }

    public static JSONObject success(PagePojo<?> pagePojo) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.OK);
        json.put("msg", "success");
        json.put("data", JSONArray.toJSON(pagePojo.getPageData()));
        json.put("count", pagePojo.getTotal());
        return json;
    }

    public static JSONObject success(String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.OK);
        json.put("msg", msg);
        json.put("data", data);
        return json;
    }

    public static JSONObject success(String msg, PagePojo<?> pagePojo) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.OK);
        json.put("msg", msg);
        json.put("data", JSONArray.toJSON(pagePojo.getPageData()));
        json.put("count", pagePojo.getTotal());
        return json;
    }

    public static JSONObject success(String msg, List<?> list) {
        JSONObject json = new JSONObject();
        json.put("code", JsonBean.OK);
        json.put("msg", msg);
        json.put("data", JSONArray.toJSON(list));
        json.put("count", list.size());
        return json;
    }

}
