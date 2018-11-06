package com.xula.base.utils;

import okhttp3.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * get 请求 entity
     *
     * @param url
     * @param data
     * @param heads
     * @return
     * @throws IOException
     */
    public static String httpGet(String url, Map<String, String> data, Map<String, String> heads) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if (data != null && !data.isEmpty()) {
            char ch = url.indexOf('?') == (-1) ? '?' : '&';
            url = url + ch + urlEncode(data);
            builder = builder.url(url);
        }
        if (!CollectionUtils.isEmpty(heads)) {
            builder = builder.headers(Headers.of(heads));
        }
        Response response = client.newCall(builder.build()).execute();
        return response.body().string();
    }

    /**
     * post 请求
     *
     * @param url
     * @param data
     * @param heads
     * @return
     * @throws IOException
     */
    public static String httpPostByJson(String url, Map<String, String> data, Map<String, String> heads) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if (!CollectionUtils.isEmpty(heads)) {
            builder = builder.headers(Headers.of(heads));
        }
        if (!CollectionUtils.isEmpty(data)) {
            System.out.println(com.alibaba.fastjson.JSON.toJSONString(data));
            RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(data));
            builder = builder.post(body);
        }
        Response response = client.newCall(builder.build()).execute();
        return response.body().string();
    }


    /**
     * post 请求 通过 key-value 传值
     *
     * @param url
     * @param data
     * @param heads
     * @return
     * @throws IOException
     */
    public static String httpPostByKeyValue(String url, Map<String, String> data, Map<String, String> heads) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if (!CollectionUtils.isEmpty(heads)) {
            builder = builder.headers(Headers.of(heads));
        }
        if (!CollectionUtils.isEmpty(data)) {
            FormBody.Builder formBody = new FormBody.Builder();
            data.forEach((s, s2) -> formBody.add(s, s2));
            builder = builder.post(formBody.build());
        }
        Response response = client.newCall(builder.build()).execute();
        return response.body().string();
    }

    /**
     * Encode url key-value pairs sorted by key.
     *
     * @param params
     * @return
     */
    public static String urlEncode(Map<String, String> params) {
        List list = new ArrayList();
        for (Map.Entry<String, String> s : params.entrySet()) {
            list.add(s.getKey() + "=" + urlEncode(s.getValue()));
        }
        return StringUtils.join(list, "&");
    }

    /**
     * url 解码
     *
     * @param s
     * @return
     */
    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * url 编号
     *
     * @param s
     * @return
     */
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
