package com.xula.controller.sysuser;


import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.SysUserConstant;
import com.xula.controller.BaseController;
import com.xula.entity.SysAction;
import com.xula.service.sys.sysuser.ISysUserService;
import com.xula.base.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 系统用户管理
 *
 * @author caibin
 */
@Controller
@RequestMapping(value = "/sysuser")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;
    @RequiresPermissions("sysuser:index")
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
        return "modules/sysuser/index";
    }

    /**
     * datagrid 绑定数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysuser:list")
    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject list(HttpServletRequest request, @RequestParam Map<String, Object> map) {
        //参数获取
        Query query = new Query(map);
        String name = ReqUtils.getParam(request, "name", null);
        String realName = ReqUtils.getParam(request, "realName", null);
        String mobile = ReqUtils.getParam(request, "mobile", null);
        String createTime = ReqUtils.getParam(request, "createTime", null);
        String lastLoginTime = ReqUtils.getParam(request, "lastLoginTime", null);
        Integer isValid = ReqUtils.getParamToInteger(request, "isValid", null);

        query.put("name", name);
        query.put("realName", realName);
        query.put("mobile", mobile);
        query.put("createTime", createTime);
        query.put("lastLoginTime", lastLoginTime);
        query.put("isValid", isValid);
        PagePojo<SysAction.SysUser> page = sysUserService.getSysUsers(query);

        //render结果
        return JsonBean.success(page);
    }

    /**
     * 添加数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysuser:add")
    @RequestMapping(value = "/add")
    @ResponseBody
    public JSONObject add(HttpServletRequest request, SysAction.SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getName())) {
            return JsonBean.error("用户名必填");
        }
        if (StringUtils.isBlank(sysUser.getPswd())) {
            return JsonBean.error("密码必填");
        }
        if (StringUtils.isBlank(sysUser.getRealName())) {
            return JsonBean.error("真实姓名必填");
        }
        if (StringUtils.isBlank(sysUser.getMobile())) {
            return JsonBean.error("手机号必填");
        }
        if (StringUtils.isBlank(sysUser.getEmail())) {
            return JsonBean.error("联系邮箱必填");
        }
        if (sysUserService.getSysUser(sysUser.getName()) != null) {
            return JsonBean.error("用户名已被占用");
        }

        int loginUid = ShiroUtils.getUserId();
        Date date = new Date();
        sysUser.setCreateTime(date);    //创建时间
        sysUser.setLastLoginTime(date); //最后登录时间
        sysUser.setCreateUid(loginUid); //创建人
        sysUser.setType(SysUserConstant.USET_TYPE_ROOT);

        RecordBean<SysAction.SysUser> result = sysUserService.addSysUsers(sysUser);

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
    @RequiresPermissions("sysuser:getView")
    @RequestMapping(value = "/getView")
    public String getView(HttpServletRequest request, Model model) {
        Integer uid = ReqUtils.getParamToInteger(request, "uid", null);
        SysAction.SysUser sysUser = new SysAction.SysUser();
        if (uid != null) {
            sysUser = sysUserService.getSysUser(uid);
        }
        model.addAttribute("data", sysUser);
        return "modules/sysuser/_edit";
    }

    /**
     * 编辑数据
     *
     * @param request
     * @return
     */
    @RequiresPermissions("sysuser:edit")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JSONObject edit(HttpServletRequest request, SysAction.SysUser sysUser) {
        if (StringUtils.isBlank(sysUser.getName())) {
            return JsonBean.error("用户名必填");
        }
        if (StringUtils.isBlank(sysUser.getRealName())) {
            return JsonBean.error("真实姓名必填");
        }
        if (StringUtils.isBlank(sysUser.getMobile())) {
            return JsonBean.error("手机号必填");
        }
        if (StringUtils.isBlank(sysUser.getEmail())) {
            return JsonBean.error("联系邮箱必填");
        }
        sysUser.setUpdateTime(new Date());
        RecordBean<SysAction.SysUser> result = sysUserService.editSysUsers(sysUser);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 删除用户数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public JSONObject del(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        RecordBean<String> result = sysUserService.delSysUsers(ids);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    public JSONObject updatePwd(HttpServletRequest request, SysAction.SysUser sysUser) {
        sysUser.setUid(ShiroUtils.getUserId());
        RecordBean<SysAction.SysUser> result = sysUserService.updatePwd(sysUser);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }

    /**
     * 获取修改密码页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserInfo")
    public String getPwdView(HttpServletRequest request, Model model) {
        Integer uid = ShiroUtils.getUserId();
        SysAction.SysUser sysUser = sysUserService.getSysUser(uid);
        model.addAttribute("data", sysUser);
        return "/modules/sysuser/_info";
    }
}
