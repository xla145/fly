package com.xula.service.auth.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.cache.RedisKit;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.RecordBean;
import com.xula.entity.SysAction;
import com.xula.entity.SysUserAction;
import com.xula.entity.TreeData;
import com.xula.entity.TreeNode;
import com.xula.service.auth.IAuthService;
import com.xula.service.auth.ISysActionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * 系统权限
 *
 * @author xla
 */
@Service("ISysActionService")
public class SysActionServiceImpl implements ISysActionService {

    private Logger logger = LoggerFactory.getLogger(SysActionServiceImpl.class);

    @Autowired
    private IAuthService authService;

    /**
     * 获取功能树
     */
    @Override
    public List<TreeNode> getActionTrees() {
        Conditions conn = new Conditions("parent_id", SqlExpr.LT, 1);
        conn.add(new Conditions("status",SqlExpr.UNEQUAL,-1),SqlJoin.AND);
        List<SysAction> list = BaseDao.dao.queryForListEntity(SysAction.class, conn);
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if (list != null) {
            for (SysAction sysAction : list) {
                TreeNode treeNode = getActionTree(sysAction);
                treeNodes.add(treeNode);
            }
        }
        if (treeNodes.size() > 0) {
            treeNodes.get(0).setSpread(true);
        }
        return treeNodes;
    }



    /**
     * 获取功能树
     */
    @Override
    public List<TreeNode> getActionTrees(Integer type) {
        Conditions conn = new Conditions("parent_id", SqlExpr.LT, 1);
        conn.add(new Conditions("status",SqlExpr.UNEQUAL,-1),SqlJoin.AND);
        conn.add(new Conditions("type",SqlExpr.EQUAL,type),SqlJoin.AND);
        List<SysAction> list = BaseDao.dao.queryForListEntity(SysAction.class, conn);
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if (list != null) {
            for (SysAction sysAction : list) {
                TreeNode treeNode = getActionTree(sysAction);
                treeNodes.add(treeNode);
            }
        }
        if (treeNodes.size() > 0) {
            treeNodes.get(0).setSpread(true);
        }
        return treeNodes;
    }

    @Override
    public List<SysAction> getAllAction() {

        return BaseDao.dao.queryForListEntity(SysAction.class, new Conditions());
    }

    /**
     * 获取功能树
     */
    private TreeNode getActionTree(SysAction sysAction) {
        int id = sysAction.getId();
        TreeNode treeNode = new TreeNode();
        treeNode.setId(id);
        treeNode.setName(sysAction.getName());
        treeNode.setParentId(sysAction.getParentId());
        treeNode.setCheckboxValue(String.valueOf(id));
        TreeData treeData = new TreeData();
        treeData.setId(id);
        treeData.setOrigin(sysAction);
        treeNode.setData(treeData);
        Conditions childrenConn = new Conditions("parent_id", SqlExpr.EQUAL, id);
        childrenConn.add(new Conditions("type",SqlExpr.EQUAL,2),SqlJoin.AND);
        childrenConn.add(new Conditions("status",SqlExpr.UNEQUAL,-1),SqlJoin.AND);
        List<SysAction> childrenSysActionList = BaseDao.dao.queryForListEntity(SysAction.class, childrenConn);
        for (SysAction childrenSysAction : childrenSysActionList) {
            //递归查询所有子节点
            TreeNode tn = getActionTree(childrenSysAction);
            treeNode.getChildren().add(tn);
        }
        return treeNode;
    }

    @Override
    public SysAction getSysAction(int id) {
        return BaseDao.dao.queryForEntity(SysAction.class, id);
    }

    /**
     * 获取父级菜单
     */
    @Override
    public List<SysAction> getParentMenus() {
        Conditions conn = new Conditions("type", SqlExpr.EQUAL, AuthServiceImpl.ACTION_TYPE_MENU);
        conn.add(new Conditions("parent_id", SqlExpr.LT, 1), SqlJoin.AND);
        return BaseDao.dao.queryForListEntity(SysAction.class, conn);
    }

    /**
     * 查询用户角色权限列表
     *
     * @param
     * @return
     */
    @Override
    public List<SysUserAction> getSysUserAction(int uid) {
        Conditions conn = new Conditions("uid", SqlExpr.EQUAL, uid);
        return BaseDao.dao.queryForListEntity(SysUserAction.class, conn);
    }

    /**
     * 查询角色权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<SysUserAction> getSysUserActionByRoleId(int roleId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT b.* FROM sys_user_role a JOIN sys_user_action b ON(a.role_id = b.role_id) WHERE a.role_id = ?");
        return BaseDao.dao.queryForListEntity(SysUserAction.class,sql.toString(),roleId);
    }


    @Override
    public List<SysAction> getSysUserActionByUid(int uid) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT sa.* FROM sys_user_action sua LEFT JOIN sys_action sa ON (sa.id = sua.action_id) ");
        sql.append("WHERE sua.uid = ?");
        return BaseDao.dao.queryForListEntity(SysAction.class, sql.toString(), uid);
    }

    /**
     * 更新用户权限
     *
     * @param operateUid
     * @param uid
     * @param roleIds
     * @return
     */
    @Transactional
    @Override
    public RecordBean<String> reloadSysUserAction(int operateUid, int uid, List<Integer> roleIds, String actionIds) {
        //清除用户权限
        BaseDao.dao.delete("DELETE FROM `sys_user_action` WHERE uid = " + uid);
        //清除用户角色
        BaseDao.dao.delete("DELETE FROM `sys_user_role` WHERE uid = " + uid);
        try {
            if (roleIds == null || roleIds.size() == 0 || StringUtils.isBlank(actionIds)) {
                return RecordBean.error("用户赋权->用户角色不能为空!");
            }
            String remark = "更新角色权限：" + operateUid;
            Date date = new Date();
            StringBuffer roleSql = new StringBuffer();
            roleSql.append("INSERT INTO `sys_user_role`(uid,role_id,remark,create_time) VALUES");
            List<Object> roleList = new ArrayList<Object>();
            for (int i = 0; i < roleIds.size(); i++) {
                roleSql.append("(?,?,?,?),");
                roleList.add(uid);
                roleList.add(roleIds.get(i));
                roleList.add(remark);
                roleList.add(date);
            }
            //保存用户角色
            int result = BaseDao.dao.insert(roleSql.deleteCharAt(roleSql.length()-1).toString(),roleList.toArray());
            if (result < 1) {
                logger.error("用户赋权->保存用户角色失败!operateUid:" + operateUid + ",uid:" + uid + ",roleIds:" + StringUtils.join(roleIds, ","));
                return RecordBean.error("用户赋权->保存用户角色失败!");
            }
            JSONArray jsonArray = JSONArray.parseArray(actionIds);
            if (!jsonArray.isEmpty()) { // 当actionIds有值的时候才给用户赋权
                List<Object> list = new ArrayList<Object>();
                //保存用户权限
                StringBuffer sql = new StringBuffer("INSERT INTO `sys_user_action`(uid, action_id, role_id,create_uid,remark,create_time) VALUES ");
                List<Object> checkList = new ArrayList<Object>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String uidId = uid + "-" + jsonObject.get("id");
                    if (checkList.contains(uidId)) { // 数据库uid,actionId 要确保唯一，所以当出现重复的时候，不加入数据库
                        continue;
                    }
                    checkList.add(uidId);
                    sql.append("(?,?,?,?,?,now()),");
                    list.add(uid);
                    list.add(jsonObject.get("id"));
                    list.add(jsonObject.get("roleId"));
                    list.add(operateUid);
                    list.add(remark);
                }
                BaseDao.dao.insert(sql.deleteCharAt(sql.length()-1).toString(), list.toArray());
            }
        } catch (Exception e) {
            logger.error("用户赋权->保存用户权限失败!" + e.getMessage());
            logger.warn("用户赋权->保存用户权限失败!operateUid:" + operateUid + ",uid:" + uid + ",actionIds:" + JSON.toJSON(actionIds));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("用户赋权->保存用户权限失败!");
        }
        //更新用户权限
        authService.reload(uid);
        return RecordBean.success("保存用户角色成功!");
    }

    /**
     * 分页查询系统功能列表
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<SysAction> getSysAction(Conditions conn, int pageNo, int pageSize) {
        conn.add(new Conditions("status", SqlExpr.UNEQUAL, -1), SqlJoin.AND);
        //查询条件
        Sort sort = new Sort("weight", SqlSort.ASC);
        return BaseDao.dao.queryForListPage(SysAction.class, conn, sort, pageNo, pageSize);
    }


    /**
     * 添加系统功能
     *
     * @param
     * @return
     */
    @Override
    public RecordBean<SysAction> addSysAction(SysAction sysAction) {
        if (sysAction.getIcon().contains(".")) {
            sysAction.setIcon(sysAction.getIcon().substring(sysAction.getIcon().indexOf(".") + 1, sysAction.getIcon().length()));
        }
        Integer parentId = sysAction.getParentId();
        SysAction sysAction1 = getSysAction(parentId);
        int level = sysAction1.getLevel();
        sysAction.setLevel(level++); // 获取上一级的等级加一
        if (sysAction.getRemark() == null || StringUtils.isEmpty(sysAction.getRemark())) {
            sysAction.setRemark("按钮操作");
        }
        sysAction.setCreateTime(new Date());
        sysAction.setUpdateTime(new Date());
        sysAction.setParentName(sysAction1.getParentName());
        int result = BaseDao.dao.insert(sysAction);
        if (result == 1) {
            return RecordBean.success("success",sysAction);
        }
        return RecordBean.error("添加系统功能失败！");
    }

    /**
     * 编辑系统功能
     *
     * @param
     * @return
     */
    @Override
    public RecordBean<SysAction> editSysAction(SysAction sysAction) {
        if (sysAction.getIcon().contains(".")) {
            sysAction.setIcon(sysAction.getIcon().substring(sysAction.getIcon().indexOf(".") + 1, sysAction.getIcon().length()));
        }
        if (sysAction.getRemark() == null || StringUtils.isEmpty(sysAction.getRemark())) {
            sysAction.setRemark("按钮操作");
        }
        Integer parentId = sysAction.getParentId();
        SysAction sysAction1 = getSysAction(parentId);
        int level = sysAction1.getLevel();
        sysAction.setLevel(level++); // 获取上一级的等级加一
        sysAction.setUpdateTime(new Date());
        sysAction.setParentName(sysAction1.getParentName());
        int result = BaseDao.dao.update(sysAction);
        if (result == 1) {
            return RecordBean.success("success",sysAction);
        }
        return RecordBean.error("修改系统功能失败！");
    }

    @Override
    public RecordBean<String> delSysAction(String[] ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE sys_action SET status = -1 ,update_time = now() WHERE id IN (");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        int result = BaseDao.dao.update(sql.toString());
        if (result == 1) {
            return RecordBean.success("success");
        }
        return RecordBean.error("删除系统功能失败！");
    }



    private static String DEFAULTAUTHKEY = "com.xula.service.auth.impl.SysActionServiceImpl";

    @Autowired
    private RedisKit<List<SysAction>> redisKit;

    /**
     * 获取用户的菜单功能
     * @param uid
     * @return
     */
    @Override
    public List<SysAction> getSysUserAction(Integer uid) {
        String key = DEFAULTAUTHKEY + ".getSysUserAction." + uid;
        List<SysAction> list = redisKit.get(key);
        if (CollectionUtils.isEmpty(list)) {
            list = this.getSysUserAction(uid,key);
        }
        return list;
    }

    /**
     * 获取用户的菜单信息
     * @param uid
     * @param key
     * @return
     */
    public List<SysAction> getSysUserAction(Integer uid,String key) {
        List<SysAction> list ;
        if (uid == 1) {
            list =  this.getAllAction();
        } else {
            list = this.getSysUserActionByUid(uid);
        }
        if (!list.isEmpty()) {
            redisKit.add(key, 60 * 60, list);
        }
        return list;
    }
}
