package com.xula.controller.dict;


import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.ReqUtils;
import com.xula.controller.BaseController;
import com.xula.entity.dict.DictGroup;
import com.xula.entity.dict.DictItem;
import com.xula.service.dict.api.IDictOperService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典服务
 * @author xla
 */
@Controller
@RequestMapping(value = "/dict/item")
public class DictItemController extends BaseController {

    @Autowired
    private IDictOperService iDictService;

    /**
     * 显示字典项
     *
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("dict:item:index")
    @RequestMapping(value = "/index")
    public String itemIndex(HttpServletRequest request, Model model) {
        String code = ReqUtils.getParam(request, "code", null);
        DictGroup dictGroup = iDictService.getDictGroup(code);
        model.addAttribute("data", dictGroup);
        return "modules/dict/item_index";
    }


    /**
     * 字典组列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("dict:item:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        String code = ReqUtils.getParam(request, "code", null);
        int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
        int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
        PagePojo<DictItem> page = iDictService.getDictItemPage(code, pageNo, pageSize);
        return JsonBean.success(page);
    }


    /**
     * 字典组列表
     *
     * @return
     */
    @RequiresPermissions("dict:item:add")
    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject list(DictItem dictItem) {
        RecordBean<DictItem> result = iDictService.addDictItem(dictItem);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("ok", result.getData());
    }


    /**
     * 修改字典项
     *
     * @return
     */
    @RequiresPermissions("dict:item:edit")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JSONObject edit(DictItem dictItem) {
        RecordBean<DictItem> result = iDictService.updateDictItem(dictItem);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("ok", result.getData());
    }

    /**
     * 修改字典项
     *
     * @param request
     * @return
     */
    @RequiresPermissions("dict:item:getView")
    @RequestMapping(value = "/getView")
    public String getView(HttpServletRequest request, Model model) {
        Integer itemId = ReqUtils.getParamToInteger(request, "id", null);
        DictItem dictItem = new DictItem();
        if (itemId != null) {
            dictItem = iDictService.getDictItem(itemId);
        }
        model.addAttribute("data", dictItem);
        return "modules/dict/_add_item";
    }


    /**
     * 修改字典项
     *
     * @param request
     * @return
     */
    @RequiresPermissions("dict:item:del")
    @RequestMapping(value = "/del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request, Model model) {
        Integer itemId = ReqUtils.getParamToInteger(request, "id", null);
        RecordBean<String> result = iDictService.delDictItem(itemId);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("ok", result.getData());
    }


}
