package com.xula.controller.sysauth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.utils.*;
import com.xula.controller.BaseController;
import com.xula.entity.SysAction;
import com.xula.entity.SysRole;
import com.xula.entity.SysUserRole;
import com.xula.entity.TreeNode;
import com.xula.service.auth.ISysActionService;
import com.xula.service.auth.ISysRoleService;
import com.xula.service.sys.sysuser.ISysUserService;
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
 * 用户权限管理
 *
 * @author caibin
 */
@Controller
@RequestMapping(value = "/sysUserAuth")
public class SysUserAuthController extends BaseController {

    @Autowired
    private ISysActionService sysActionService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserService sysUserService;


    @RequiresPermissions("sysUserAuth:index")
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        int targetUid = ReqUtils.getParamToInt(request, "targetUid", 0);
        SysAction.SysUser sysUser = sysUserService.getSysUser(targetUid);
        if (sysUser == null) {
            return renderTips(model, "用户不存在");
        }
        model.addAttribute("sysUser", sysUser);
        List<SysUserRole> userRolelist = sysRoleService.getSysUserRole(targetUid);
        List<SysRole> roleList = sysRoleService.getSysRoles();
        if (userRolelist != null && roleList != null) {
            for (SysUserRole sur : userRolelist) {
                for (int i = 0; i < roleList.size(); i++) {
                    if (sur.getRoleId().equals(roleList.get(i).getId())) {
                        roleList.get(i).setChecked(true);
                    }
                }
            }
        }

        model.addAttribute("roleList", roleList);
        return "modules/sys-auth/user-auth/index";
    }

    //获取角色权限树
    @RequestMapping(value = "/getRoleActionTree")
    @ResponseBody
    public JSONObject getRoleAction(HttpServletRequest request, @RequestParam("roleIds[]") List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() < 1) {
            return JsonBean.error("未选择角色!");
        }
        int targetUid = ReqUtils.getParamToInt(request, "targetUid", 0);

        List<TreeNode> tn = sysRoleService.getRoleActionTree(roleIds,targetUid);
        return JsonBean.success("ok", JSONArray.toJSONString(tn));
    }

    //获取角色权限树
    @RequestMapping(value = "/authSave")
    @ResponseBody
    public JSONObject authSave(HttpServletRequest request, @RequestParam("actionIds") String actionIds) {
        String[] roleIds = request.getParameterValues("roleIds[]");
        if (roleIds == null || roleIds.length == 0) {
            return JsonBean.error("用户赋权->用户角色不能为空!");
        }
        int operateUid = ShiroUtils.getUserId();
        int targetUid = ReqUtils.getParamToInt(request, "targetUid", 0);
        RecordBean<String> result = sysActionService.reloadSysUserAction(operateUid, targetUid, CommonUtil.arraysToList(roleIds),actionIds);
        if (result.isSuccessCode()) {
            JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }
}
