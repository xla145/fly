package com.xula.controller.sysauth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.base.utils.ReqUtils;
import com.xula.base.utils.ShiroUtils;
import com.xula.entity.SysRole;
import com.xula.entity.SysRoleAction;
import com.xula.entity.TreeNode;
import com.xula.service.auth.ISysActionService;
import com.xula.service.auth.ISysRoleService;
import com.xula.service.model.SysUserRoleModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色权限管理
 *
 * @author caibin
 */
@Controller
@RequestMapping(value = "/sysRoleAuth")
public class SysRoleAuthController {

    @Autowired
    private ISysActionService sysActionService;
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        List<TreeNode> list = sysActionService.getActionTrees();
        List<SysRole> roleList = sysRoleService.getSysRoles();
        model.addAttribute("roleList", roleList);
        model.addAttribute("treeNode", JSONArray.toJSONString(list));

        return "modules/sys-auth/role-auth/index";
    }

    //获取角色权限列表
    @RequestMapping(value = "/getRoleAction")
    @ResponseBody
    public JSONObject getRoleAction(HttpServletRequest request, Model model) {
        int roleId = ReqUtils.getParamToInt(request, "roleId", 0);
        if (roleId < 1) {
            return JsonBean.error("角色不存在!");
        }
        JSONArray arr = new JSONArray();
        List<SysUserRoleModel> userRole = sysRoleService.getRoleUserNum(roleId);
        List<SysRoleAction> list = sysRoleService.getSysRoleAction(roleId);
        if (list != null) {
            for (SysRoleAction sysRoleAction : list) {
                arr.add(sysRoleAction.getActionId());
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("arr",arr);
        jsonObject.put("userRole",userRole);
        return JsonBean.success("ok", jsonObject);
    }

    //更新角色权限
    @RequiresPermissions("sysRoleAuth:editRoleAction")
    @RequestMapping(value = "/editRoleAction")
    @ResponseBody
    public JSONObject editRoleAction(HttpServletRequest request, @RequestParam("actionIds[]") List<Integer> actionIds) {
        int uid = ShiroUtils.getUserId();
        int roleId = ReqUtils.getParamToInt(request, "roleId", 0);
        if (roleId < 1) {
            return JsonBean.error("角色不存在!");
        }
        if (actionIds == null || actionIds.size() < 1) {
            return JsonBean.error("未选择功能菜单！");
        }
        RecordBean<String> result = sysRoleService.reloadSysRoleAction(roleId, uid, actionIds);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }
}
