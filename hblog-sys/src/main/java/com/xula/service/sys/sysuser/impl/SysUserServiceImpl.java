package com.xula.service.sys.sysuser.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.base.cache.MCache;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.RecordBean;
import com.xula.base.constant.SysUserConstant;
import com.xula.entity.SysAction;
import com.xula.service.sys.sysuser.ISysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户service
 *
 * @author caibin
 */
@Service("ISysUserService")
public class SysUserServiceImpl implements ISysUserService {
    /**
     * 登录
     *
     * @param username
     * @param pswd
     * @return
     */
    @Override
    public RecordBean<SysAction.SysUser> login(String username, String pswd) {
        String cipher = CommonUtil.md5(pswd);
        Conditions conn = new Conditions("name", SqlExpr.EQUAL, username);
        conn.add(new Conditions("pswd", SqlExpr.EQUAL, cipher), SqlJoin.AND);
        SysAction.SysUser sysUser = BaseDao.dao.queryForEntity(SysAction.SysUser.class, conn);
        if (sysUser == null) {
            return RecordBean.error("用户名或密码错误！");
        }
        if (sysUser.getIsValid() != SysUserConstant.USET_DALID_YES) {
            return RecordBean.error("登录失败，用户无效！");
        }
        return RecordBean.success("成功登录", sysUser);
    }


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public SysAction.SysUser getSysUser(String username) {
        Conditions conn = new Conditions("name", SqlExpr.EQUAL, username);
        SysAction.SysUser sysUser = BaseDao.dao.queryForEntity(SysAction.SysUser.class, conn);
        return sysUser;
    }

    /**
     * 根据uid查询系统用户信息
     *
     * @param
     * @param
     * @return
     */
    @MCache(expire = 30)
    @Override
    public SysAction.SysUser getSysUser(int uid) {
        SysAction.SysUser sysUser = BaseDao.dao.queryForEntity(SysAction.SysUser.class, uid);
        return sysUser;
    }

    /**
     * 分页查询系统用户
     *
     * @param map
     * @return
     */
    @Override
    @MCache(expire = 30)
    public PagePojo<SysAction.SysUser> getSysUsers(Map<String, Object> map) {

        int pageNo = (int) map.get("pageNo");
        int pageSize = (int) map.get("pageSize");
        //查询条件
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("SELECT * FROM sys_user ");
        String name = (String) map.get("name");
        String realName = (String) map.get("realName");
        String mobile = (String) map.get("mobile");
        String createTime = (String) map.get("createTime");
        String lastLoginTime = (String) map.get("lastLoginTime");
        Integer isValid = (Integer) map.get("isValid");

        sql.append("WHERE 1=1 ");
        if (StringUtils.isNotBlank(name) && StringUtils.isNotEmpty(name)) {
            sql.append("AND name like ? ");
            params.add(CommonUtil.queryLike(name.trim()));
        }
        if (StringUtils.isNotBlank(realName) && StringUtils.isNotEmpty(realName)) {
            sql.append("AND real_name like ? ");
            params.add(CommonUtil.queryLike(realName.trim()));
        }
        if (StringUtils.isNotBlank(mobile) && StringUtils.isNotEmpty(mobile)) {
            sql.append("AND mobile = ?");
            params.add(CommonUtil.queryLike(mobile.trim()));
        }
        if (StringUtils.isNotBlank(createTime) && StringUtils.isNotEmpty(createTime)) {
            sql.append("AND create_time BETWEEN ? AND ? ");
            String[] time = createTime.split(" ~ ");
            params.add(time[0]);
            params.add(time[1]);
        }
        if (StringUtils.isNotBlank(lastLoginTime) && StringUtils.isNotEmpty(lastLoginTime)) {
            sql.append("AND last_login_time BETWEEN ? AND ? ");
            String[] time = lastLoginTime.split(" ~ ");
            params.add(time[0]);
            params.add(time[1]);
        }
        if (isValid != null) {
            sql.append("AND is_valid = ? ");
            params.add(isValid);
        }
        Sort sort = new Sort("uid", SqlSort.ASC);
        return BaseDao.dao.queryForListPage(SysAction.SysUser.class, sql.toString(), params, sort, pageNo, pageSize);
    }

    /**
     * 添加 系统用户
     *
     * @param sysUser
     */
    @Override
    public RecordBean<SysAction.SysUser> addSysUsers(SysAction.SysUser sysUser) {
        String cipher = CommonUtil.md5(sysUser.getPswd());
        sysUser.setPswd(cipher);
        int result = BaseDao.dao.insert(sysUser);
        if (result < 1) {
            return RecordBean.error("添加系统用户失败！");
        }
        return RecordBean.success("添加系统用户成功！");
    }

    /**
     * 编辑 系统用户
     *
     * @param sysUser
     */
    @Override
    public RecordBean<SysAction.SysUser> editSysUsers(SysAction.SysUser sysUser) {
        if (sysUser.getIsValid() == null) {
            sysUser.setIsValid(SysUserConstant.USET_DALID_NO);
        }
        int result = BaseDao.dao.update(sysUser);
        if (result < 1) {
            return RecordBean.error("修改系统用户失败！");
        }
        return RecordBean.success("修改系统用户成功！");
    }

    /**
     * 删除用户信息
     *
     * @param ids
     * @return
     */
    @Override
    public RecordBean<String> delSysUsers(String[] ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE sys_user set is_valid = 0 AND update_time = now() WHERE uid IN(");
        sql.append(CommonUtil.arrayToSqlIn(ids));
        sql.append(")");
        int result = BaseDao.dao.update(sql.toString());
        if (result < 1) {
            return RecordBean.error("删除系统用户失败！");
        }
        return RecordBean.error("删除系统用户成功！");
    }

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    @Override
    public RecordBean<SysAction.SysUser> updatePwd(SysAction.SysUser sysUser) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE sys_user SET pswd = ? ,update_time = now() WHERE uid = ?");
        int result = BaseDao.dao.update(sql.toString(), CommonUtil.md5(sysUser.getPswd()), sysUser.getUid());
        if (result < 1) {
            return RecordBean.error("修改密码失败！");
        }
        return RecordBean.error("修改密码成功！");
    }

    /**
     * 根据用户手机号获取用户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public SysAction.SysUser getSysUserByMobile(String mobile) {
        return BaseDao.dao.queryForEntity(SysAction.SysUser.class, new Conditions("mobile", SqlExpr.EQUAL, mobile));
    }
}
