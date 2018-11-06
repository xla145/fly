package com.xula.service.auth;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.SysRole;
import com.xula.entity.SysRoleAction;
import com.xula.entity.SysUserRole;
import com.xula.entity.TreeNode;
import com.xula.service.model.SysUserRoleModel;

import java.util.List;


/**
 * 系统权限
 *
 * @author xla
 */
public interface ISysRoleService {

    /**
     * 查询角色列表
     * @return
     */
     List<SysRole> getSysRoles();

    /**
     * 查询角色列表
     *
     * @param roleId
     * @return
     */
     SysRole getSysRole(Integer roleId);

    /**
     * 查询角色权限
     *
     * @param roleId
     * @return
     */
     List<SysRoleAction> getSysRoleAction(int roleId);


    /**
     * 查询用户角色列表
     *
     * @param uid
     * @return
     */
     List<SysUserRole> getSysUserRole(int uid);

    /**
     * 查询角色权限树
     *
     * @param roleIds
     * @param uid
     * @return
     */
     List<TreeNode> getRoleActionTree(List<Integer> roleIds, int uid);

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param actionIds
     * @param actionIds
     * @return
     */
     RecordBean<String> reloadSysRoleAction(int roleId, int operateUid, List<Integer> actionIds);


    /**
     * 分页获取角色信息
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
     PagePojo<SysRole> getSysRolePage(Conditions conn, Integer pageNo, Integer pageSize);


    /**
     * 添加角色信息
     *
     * @param sysRole
     * @return
     */
     RecordBean<SysRole> addSysRole(SysRole sysRole);

    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return
     */
     RecordBean<SysRole> editSysRole(SysRole sysRole);

    /**
     * 删除角色信息
     *
     * @param ids
     * @return
     */
     RecordBean<String> delSysRole(String[] ids);

    /**
     * 根据角色ID查询拥有此角色的有多少用户
     * @param id
     * @return
     */
     List<SysUserRoleModel> getRoleUserNum(Integer id);
}
