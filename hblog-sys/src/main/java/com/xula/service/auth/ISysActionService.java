package com.xula.service.auth;


import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.SysAction;
import com.xula.entity.SysUserAction;
import com.xula.entity.TreeNode;

import java.util.List;


/**
 * 系统权限
 *
 * @author xla
 */
public interface ISysActionService {



    /**
     * 获取菜单详细信息
     * @return
     */
    List<TreeNode> getActionTrees();


    /**
     * 获取菜单详细信息
     * @param type
     * @return
     */
     List<TreeNode> getActionTrees(Integer type);

    /**
     * 获取菜单详细信息
     * @return
     */
     List<SysAction> getAllAction();

    /**
     * 获取菜单详细信息
     * @param id
     * @return
     */
     SysAction getSysAction(int id);

    /**
     * 获取父级菜单
     * @return
     */
     List<SysAction> getParentMenus();

    /**
     * 查询用户角色权限列表
     *
     * @param uid
     * @return
     */
     List<SysUserAction> getSysUserAction(int uid);


    /**
     * 查询角色获取权限列表
     *
     * @param roleId
     * @return
     */
     List<SysUserAction> getSysUserActionByRoleId(int roleId);


    /**
     * 查询用户角色权限列表
     *
     * @param uid
     * @return
     */
     List<SysAction> getSysUserActionByUid(int uid);

    /**
     * 更新用户权限
     *
     * @param uid
     * @param roleIds
     * @param operateUid
     * @param actionIds
     * @return
     */
     RecordBean<String> reloadSysUserAction(int operateUid, int uid, List<Integer> roleIds, String actionIds);

    /**
     * 分页查询系统功能列表
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
     PagePojo<SysAction> getSysAction(Conditions conn, int pageNo, int pageSize);


    /**
     * 添加系统功能
     *
     * @param sysAction
     * @return
     */
     RecordBean<SysAction> addSysAction(SysAction sysAction);

    /**
     * 编辑系统功能
     *
     * @param sysAction
     * @return
     */
     RecordBean<SysAction> editSysAction(SysAction sysAction);

    /**
     * 删除系统功能
     *
     * @param ids
     * @return
     */
     RecordBean<String> delSysAction(String[] ids);


    /**
     * 获取系统功能
     * @param uid
     * @return
     */
    List<SysAction> getSysUserAction(Integer uid);

}
