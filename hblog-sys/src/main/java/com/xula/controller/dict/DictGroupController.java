package com.xula.controller.dict;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.ReqUtils;
import com.xula.base.utils.ShiroUtils;
import com.xula.controller.BaseController;
import com.xula.entity.dict.DictGroup;
import com.xula.service.dict.api.IDictOperService;
import com.xula.service.dict.constant.DictConstant;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "/dict/group")
public class DictGroupController extends BaseController {

    @Autowired
    private IDictOperService iDictService;

    /**
     * 显示字典组
     *
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("dict:group:index")
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        return "modules/dict/index";
    }

    /**
     * 字典组列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions("dict:group:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        Integer uid = ShiroUtils.getUserId();
        int pageSize = ReqUtils.getParamToInt(request, "pageSize", 15);
        int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
        String code = ReqUtils.getParam(request, "code", null);
        Conditions con = new Conditions();
        if (StringUtils.isNotEmpty(code)) {
            con.add(new Conditions("code", SqlExpr.EQUAL, code),SqlJoin.AND);
        }
        if (uid != 1) {
            con.add(new Conditions("belong", SqlExpr.EQUAL, DictConstant.DICT_BELONG_OPRATION), SqlJoin.AND);
        }
        PagePojo<DictGroup> page = iDictService.getDictGroupPage(con, pageNo, pageSize);
        return JsonBean.success(page);
    }

    /**
     * 字典组列表
     *
     * @param request
     * @return
     */
    @RequiresPermissions(value = "dict:group:add")
    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject list(HttpServletRequest request, DictGroup dictGroup) {
        RecordBean<DictGroup> result = iDictService.addDictGroup(dictGroup);
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
    @RequiresPermissions(value = "dict:group:edit")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JSONObject edit(HttpServletRequest request, DictGroup dictGroup) {
        RecordBean<DictGroup> result = iDictService.updateDictGroup(dictGroup);
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
    @RequiresPermissions("dict:group:getView")
    @RequestMapping(value = "/getView")
    public String getView(HttpServletRequest request, Model model) {
        String code = ReqUtils.getParam(request, "code", null);
        DictGroup dictGroup = new DictGroup();
        if (StringUtils.isNotBlank(code)) {
            dictGroup = iDictService.getDictGroup(code);
        }
        model.addAttribute("data", dictGroup);
        return "modules/dict/_add";
    }


    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkCode")
    @ResponseBody
    public JSONObject checkCode(HttpServletRequest request) {
        Integer groupId = ReqUtils.getParamToInteger(request, "groupId", null);
        String code = ReqUtils.getParam(request, "code", null);
        RecordBean<String> result = iDictService.checkGroupCode(code, groupId);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

}
