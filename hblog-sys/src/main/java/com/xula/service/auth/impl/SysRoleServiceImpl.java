package com.xula.service.auth.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.cache.MCache;
import com.xula.base.utils.CommonUtil;
import com.xula.entity.*;
import com.xula.service.auth.ISysActionService;
import com.xula.service.auth.ISysRoleService;
import com.xula.service.model.SysActionModel;
import com.xula.service.model.SysUserRoleModel;
import com.xula.base.utils.RecordBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;


/**
 * 系统权限
 *
 * @author xla
 */
@Service("ISysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {

    private static Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private ISysActionService sysActionService;

    /**
     * 查询所有角色
     */
    @MCache(expire = 90)
    @Override
    public List<SysRole> getSysRoles() {
        Conditions conn = new Conditions("id", SqlExpr.GT, 0);
        return BaseDao.dao.queryForListEntity(SysRole.class, conn);
    }

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRole getSysRole(Integer roleId) {
        return BaseDao.dao.queryForEntity(SysRole.class, roleId);
    }

    /**
     * 查询角色权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysRoleAction> getSysRoleAction(int roleId) {
        Conditions conn = new Conditions("role_id", SqlExpr.EQUAL, roleId);
        return BaseDao.dao.queryForListEntity(SysRoleAction.class, conn);
    }

    /**
     * 查询用户角色列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<SysUserRole> getSysUserRole(int uid) {
        Conditions conn = new Conditions("uid", SqlExpr.EQUAL, uid);
        return BaseDao.dao.queryForListEntity(SysUserRole.class, conn);
    }

    /**
     * 查询角色权限树
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<TreeNode> getRoleActionTree(List<Integer> roleIds, int uid) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if (roleIds == null || roleIds.size() < 1) {
            return treeNodes;
        }
        StringBuffer sql = new StringBuffer("SELECT DISTINCT a.*,b.`role_id` ");
        sql.append(" FROM `sys_action` AS a INNER JOIN `sys_role_action` AS b ON a.`id` = b.`action_id` ");
        sql.append(" WHERE b.`role_id` IN(");
        sql.append(CommonUtil.arrayToSqlIn(roleIds.toArray()));
        sql.append(") AND parent_id < 1 ");
        List<SysActionModel> list = BaseDao.dao.queryForListEntity(SysActionModel.class, sql.toString());
        List<SysUserAction> sysUserActionList = sysActionService.getSysUserAction(uid);
        Set<Integer> set = new HashSet<Integer>();
        for (SysUserAction sysUserAction : sysUserActionList) {
            set.add(sysUserAction.getActionId());
        }
        if (list != null) {
            for (SysActionModel sysAction : list) {
                TreeNode treeNode = getRoleActionTree(sysAction, roleIds, set);
                treeNodes.add(treeNode);
            }
        }
        if (treeNodes.size() > 0) {
            treeNodes.get(0).setSpread(true);
        }
        return treeNodes;
    }

    /**
     * 更新角色权限
     *
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public RecordBean<String> reloadSysRoleAction(int roleId, int operateUid, List<Integer> actionIds) {
        try {
            // 清除所有角色权限
            BaseDao.dao.update("DELETE FROM sys_role_action WHERE role_id = ?", roleId);
            List<SysRoleAction> sysRoleActions = new ArrayList<SysRoleAction>();
            Date date = new Date();
            for (int actionId : actionIds) {
                SysRoleAction sra = new SysRoleAction();
                sra.setActionId(actionId);
                sra.setRoleId(roleId);
                sra.setCreateTime(date);
                sra.setCreateUid(operateUid);
                sysRoleActions.add(sra);
            }
            // 插入新的角色权限
            int i = BaseDao.dao.insert(sysRoleActions);
            logger.info("更新角色权限－－>插入角色权限，result:" + i + ",roleId:" + roleId + ",operateUid:" + operateUid);
            if (i > 0) {
                // 根据roleId 找到useId 然后 通过 sys_user_action 获取的actionId 跟 sys_role_action 对比 筛选出需要改变的数据
                List<SysUserAction> sysUserActionList = sysActionService.getSysUserActionByRoleId(roleId);
                // 筛选出要删除的 actionId
                StringBuffer sql = new StringBuffer("DELETE FROM `sys_user_action` WHERE role_id = ? ");
                sql.append("AND action_id IN ('");
                for (SysUserAction sysUserAction : sysUserActionList) {
                    if (!actionIds.contains(sysUserAction.getActionId())) {
                        sql.append(sysUserAction.getActionId() + "','");
                    }
                }
                sql.append("')");
                int result = BaseDao.dao.update(sql.toString(), roleId);
                logger.info("更新角色权限，result:" + result + ",roleId:" + roleId + ",operateUid:" + operateUid);
            }
        } catch (Exception e) {
            logger.error("更新角色权限异常！原因是【" + e.getMessage() + "】");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("更新角色权限异常！");
        }
        return RecordBean.success("更新角色权限成功！");

    }

    /**
     * 分页获取角色
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<SysRole> getSysRolePage(Conditions conn, Integer pageNo, Integer pageSize) {
        return BaseDao.dao.queryForListPage(SysRole.class, conn, null, pageNo, pageSize);
    }


    /**
     * 添加角色信息
     *
     * @param sysRole
     * @return
     */
    @Override
    public RecordBean<SysRole> addSysRole(SysRole sysRole) {
        int result = BaseDao.dao.insert(sysRole);
        if (result == 0) {
            return RecordBean.error("添加角色信息失败！");
        }
        return RecordBean.success("", sysRole);
    }


    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    @Override
    public RecordBean<SysRole> editSysRole(SysRole sysRole) {
        int result = BaseDao.dao.update(sysRole);
        if (result == 0) {
            return RecordBean.error("添加角色信息失败！");
        }
        return RecordBean.success("", sysRole);
    }

    /**
     * 删除角色信息（删除角色，目前指支持单个删除）
     * 删除角色，目前指支持单个删除
     * 删除角色 因为要删除关联的用户信息和功能信息（尽量少去操作）
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public RecordBean<String> delSysRole(String[] ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM sys_role WHERE id IN(");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        try {
            int result = BaseDao.dao.update(sql.toString());
            if (result == 0) {
                return RecordBean.error("删除角色信息失败！");
            }
            // 清除角色功能
            BaseDao.dao.update("DELETE FROM  sys_role_action WHERE role_id IN (" + CommonUtil.arrayToSqlIn(ids) + ")");
            // 清除用户角色
            BaseDao.dao.delete("DELETE FROM `sys_user_role` WHERE role_id  IN (" + CommonUtil.arrayToSqlIn(ids) + ")");
            // 清除用户功能
            BaseDao.dao.update("DELETE FROM `sys_user_action` WHERE role_id IN (" + CommonUtil.arrayToSqlIn(ids) + ")");
        } catch (Exception e) {
            logger.error("删除角色信息失败！原因是【" + e.getMessage() + "】");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("删除角色信息失败！");
        }
        return RecordBean.success("删除角色信息成功！");
    }

    /**
     * 获取功能树
     */
    private TreeNode getRoleActionTree(SysActionModel sysAction, List<Integer> roleIds, Set<Integer> set) {
        int id = sysAction.getId();
        TreeNode treeNode = new TreeNode();
        treeNode.setId(id);
        treeNode.setName(sysAction.getName());
        treeNode.setParentId(sysAction.getParentId());
        treeNode.setCheckboxValue(String.valueOf(id));
        treeNode.setRoleId(sysAction.getRoleId());
        if (set.contains(id)) {
            treeNode.setChecked(true);
        }
        StringBuffer sql = new StringBuffer("SELECT DISTINCT a.*,b.`role_id` ");
        sql.append(" FROM `sys_action` AS a INNER JOIN `sys_role_action` AS b ON a.`id` = b.`action_id`");
        sql.append(" WHERE b.`role_id` IN(");
        sql.append(CommonUtil.arrayToSqlIn(roleIds.toArray()));
        sql.append(") AND parent_id = ?");
        List<SysActionModel> childrenSysActionList = BaseDao.dao.queryForListEntity(SysActionModel.class, sql.toString(), id);
        for (SysActionModel childrenSysAction : childrenSysActionList) {
            //递归查询所有子节点
            TreeNode tn = getRoleActionTree(childrenSysAction, roleIds, set);
            treeNode.getChildren().add(tn);
        }
        return treeNode;
    }

    @Override
    public List<SysUserRoleModel> getRoleUserNum(Integer id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(u.uid)num ,GROUP_CONCAT(u.`real_name`)name FROM sys_user u, sys_user_role ur WHERE u.uid = ur.uid ");
        if (id != null) {
            sql.append("AND ur.role_id = ?");
        }
        return BaseDao.dao.queryForListEntity(SysUserRoleModel.class, sql.toString(), id);
    }
}