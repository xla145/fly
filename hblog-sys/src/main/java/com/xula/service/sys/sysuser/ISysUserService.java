package com.xula.service.sys.sysuser;


import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.SysAction;

import java.util.Map;

/**
 * 系统用户service
 *
 * @author xla
 */
public interface ISysUserService {

    /**
     * 登录
     *
     * @param username
     * @param pswd
     * @return
     */
    RecordBean<SysAction.SysUser> login(String username, String pswd);


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysAction.SysUser getSysUser(String username);


    /**
     * 根据uid查询系统用户信息
     *
     * @param uid
     * @return
     */
    SysAction.SysUser getSysUser(int uid);

    /**
     * 分页查询系统用户
     *
     * @param map
     * @return
     */
    PagePojo<SysAction.SysUser> getSysUsers(Map<String, Object> map);


    /**
     * 添加系统用户
     *
     * @param sysUser
     * @return
     */
    RecordBean<SysAction.SysUser> addSysUsers(SysAction.SysUser sysUser);

    /**
     * 编辑系统用户
     *
     * @param sysUser
     * @return
     */
    RecordBean<SysAction.SysUser> editSysUsers(SysAction.SysUser sysUser);

    /**
     * 删除用户数据
     *
     * @param ids
     * @return
     */
    RecordBean<String> delSysUsers(String[] ids);

    /**
     * 编辑系统用户
     *
     * @param sysUser
     * @return
     */
    RecordBean<SysAction.SysUser> updatePwd(SysAction.SysUser sysUser);

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    SysAction.SysUser getSysUserByMobile(String mobile);
}
