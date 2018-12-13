package com.xula.controller.sysauth;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlJoin;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.ReqUtils;
import com.xula.entity.Option;
import com.xula.entity.SysAction;
import com.xula.entity.TreeNode;
import com.xula.service.auth.ISysActionService;
import com.xula.service.auth.impl.AuthServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统功能管理
 *
 * @author caibin
 */
@Controller
@RequestMapping(value = "/sysAction")
public class SysActionsController {

    @Autowired
    private ISysActionService sysActionService;

    @RequiresPermissions("sysAction:index")
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        return "modules/sys-auth/action/index";
    }

    /**
     * datagrid 绑定数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysAction:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject data(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageNo = ReqUtils.getParamToInt(request, "pageNo", 1);
        int pageSize = ReqUtils.getParamToInt(request, "pageSize", 2000);
        int parentId = ReqUtils.getParamToInt(request, "parentId", -1);
        Conditions conn = new Conditions("parent_id", SqlExpr.EQUAL, parentId);
        PagePojo<SysAction> page = sysActionService.getSysAction(conn, pageNo, pageSize);
        return JsonBean.success(page);
    }



    @RequestMapping(value = "/actionList")
    @ResponseBody
    public JSONObject actionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<TreeNode> list = sysActionService.getActionTrees(2);
        return JsonBean.success("success",list);
    }

    /**
     * 添加数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysAction:add")
    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, SysAction sysAction) {
        if (StringUtils.isBlank(sysAction.getName())) {
            return JsonBean.error("功能名称必填");
        }
        if (sysAction.getType() == null) {
            return JsonBean.error("功能类型必选");
        }
        if (sysAction.getParentId() == null) {
            return JsonBean.error("父级节点必选");
        }
        if (sysAction.getType() == AuthServiceImpl.ACTION_TYPE_MENU) {
            if (StringUtils.isBlank(sysAction.getUrl())) {
                return JsonBean.error("系统菜单url必填");
            }
            if (StringUtils.isBlank(sysAction.getRemark())) {
                return JsonBean.error("功能描述必填");
            }
        }

        sysAction.setCreateTime(new Date());    //创建时间

        RecordBean<SysAction> result = sysActionService.addSysAction(sysAction);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 编辑页面
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysAction:getView")
    @RequestMapping(value = "/getView")
    public String editView(HttpServletRequest request, Model model) {
        Integer id = ReqUtils.getParamToInteger(request, "id", null);
        SysAction sysAction = new SysAction();
        List<SysAction> list = sysActionService.getParentMenus();
        List<Option> options = new ArrayList<Option>();
        if (list != null) {
            for (SysAction sa : list) {
                Option option = new Option();
                option.setId(sa.getId() + "");
                option.setName(sa.getName());
                options.add(option);
            }
        }
        model.addAttribute("options", options);
        if (id != null) {
            sysAction = sysActionService.getSysAction(id);
        }
        model.addAttribute("data", sysAction);
        return "modules/sys-auth/action/_add";
    }

    /**
     * 编辑数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysAction:edit")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JSONObject edit(HttpServletRequest request, SysAction sysAction) {
        if (StringUtils.isBlank(sysAction.getName())) {
            return JsonBean.error("功能名称必填");
        }
        if (sysAction.getType() == null) {
            return JsonBean.error("功能类型必选");
        }
        if (sysAction.getParentId() == null) {
            return JsonBean.error("父级节点必选");
        }
        if (sysAction.getType() == AuthServiceImpl.ACTION_TYPE_MENU) {
            if (StringUtils.isBlank(sysAction.getUrl())) {
                return JsonBean.error("系统菜单url必填");
            }
            if (StringUtils.isBlank(sysAction.getRemark())) {
                return JsonBean.error("功能描述必填");
            }
        }
        RecordBean<SysAction> result = sysActionService.editSysAction(sysAction);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    @RequiresPermissions("sysAction:del")
    @RequestMapping(value = "/del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        RecordBean<String> result = sysActionService.delSysAction(ids);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    @RequestMapping(value = "/tree")
    public String tree(HttpServletRequest request, Model model) {
        List<TreeNode> list = sysActionService.getActionTrees();
        model.addAttribute("treeNode", JSONArray.toJSONString(list));
        return "modules/sys-auth/action/_tree";
    }
}
