package com.xula.base.utils;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * response json格式
 * 
 * @author xla
 *
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
	
	public static JSONObject notLogin(String msg) {
		JSONObject json = new JSONObject();
		json.put("code", 999);
		json.put("msg", msg);
		return json;
	}

	public static JSONObject notRegister(String msg) {
		JSONObject json = new JSONObject();
		json.put("code", 998);
		json.put("msg", msg);
		return json;
	}

	public static JSONObject notLogin(String msg, String authUrl) {
		JSONObject json = new JSONObject();
		json.put("code", 999);
		json.put("msg", msg);
		json.put("data", authUrl);
		return json;
	}
	
	public static JSONObject error(int code, String msg) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		return json;
	}

	public static JSONObject error(int code, String msg, Object data) {
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


	/**
	 * 设置跳转的地址
	 * @param msg
	 * @param action
	 * @return
	 */
	public static JSONObject success(String msg,String action) {
		JSONObject json = new JSONObject();
		json.put("code", JsonBean.OK);
		json.put("msg", msg);
		json.put("action", action);
		return json;
	}
	
	public static JSONObject success(String msg, Object data) {
		JSONObject json = new JSONObject();
		json.put("code", JsonBean.OK);
		json.put("msg", msg);
		json.put("data", data);
		return json;
	}
	
	public static JSONObject page(String msg, PagePojo<?> page) {
		JSONObject data = new JSONObject();
		data.put("pageNo", 1);
		data.put("pageSize", 0);
		data.put("total", 0);
		data.put("pageTotal", 0);
		data.put("pageDate", new JSONArray());
		
		if(page != null){
			List<?> list = page.getPageData();
			data.put("pageNo", page.getPageNo());
			data.put("pageSize", page.getPageSize());
			data.put("total", page.getTotal());
			data.put("pageTotal", page.getPageTotal());
			data.put("pageDate", JSON.toJSON(list));
		}
		return JsonBean.success(msg, data);
	}
}
