package com.xula.service.auth.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.cache.RedisKit;
import com.xula.entity.SysAction;
import com.xula.service.auth.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户操作认证
 *
 * @author gull
 */
@Service("IUserAuthService")
public class AuthServiceImpl implements IAuthService {

    /**
     * 1：系统功能
     **/
    public static int ACTION_TYPE_BTN = 1;
    /**
     * 2：导航菜单
     **/
    public static int ACTION_TYPE_MENU = 2;

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static String DEFAULTAUTHKEY = "com.xula.service.auth.impl.UserAuthServiceImpl";

    @Autowired
    private RedisKit<JSONArray> redisKit;


    /**
     * 获取导航菜单
     */
    @Override
    public JSONArray getAllMenus() {
        String key = DEFAULTAUTHKEY + ".getAllMenus";
        JSONArray array = redisKit.get(key);
        if (array != null) {
            return array;
        }

        Conditions conn = new Conditions("type", SqlExpr.EQUAL, AuthServiceImpl.ACTION_TYPE_MENU);
        conn.add(new Conditions("status", SqlExpr.EQUAL, 1), SqlJoin.AND);
        Sort sort = new Sort("weight", SqlSort.ASC);
        List<SysAction> list = BaseDao.dao.queryForListEntity(SysAction.class, conn, sort);
        JSONArray navArr = new JSONArray();

        if (list != null) {
            for (SysAction sa : list) {
                if (sa.getParentId() < 1) {
                    JSONObject nodeJson = new JSONObject();
                    nodeJson.put("title", sa.getName());
                    nodeJson.put("icon", sa.getIcon());
                    nodeJson.put("spread", false);
                    JSONArray childrens = new JSONArray();
                    for (SysAction sa2 : list) {
                        if (sa2.getParentId() > 0 && sa2.getParentId().equals(sa.getId())) {
                            JSONObject children = new JSONObject();
                            children.put("title", sa2.getName());
                            children.put("icon", sa2.getIcon());
                            children.put("href", sa2.getUrl());
                            childrens.add(children);
                        }
                    }
                    nodeJson.put("children", childrens);
                    navArr.add(nodeJson);
                }
            }
        }
        if (navArr != null && navArr.size() > 0) {
            redisKit.add(key, 60 * 10, navArr);
        }
        return navArr;
    }

    /**
     * 获取用户操作菜单
     *
     * @param uid
     * @return
     */
    @Override
    public JSONArray getMenu(int uid) {
        String key = DEFAULTAUTHKEY + ".getUserMenus." + uid;
        JSONArray array = redisKit.get(key);
        if (array == null) {
            array = this.getUserMenus(uid, key);
        }
        return array;
    }

    /**
     * 重新加载用户权限
     *
     * @param uid
     */
    @Override
    public void reload(int uid) {
        String key2 = DEFAULTAUTHKEY + ".getUserMenus." + uid;
        String key3 = DEFAULTAUTHKEY + ".getAllMenus";
        String key4 = DEFAULTAUTHKEY + ".getSysUserAction." + uid;

        String key =  "com.xula.service.auth.impl.SysActionServiceImpl.getSysUserAction." + uid;
        redisKit.delete(key2);
        redisKit.delete(key3);
        redisKit.delete(key4);
        redisKit.delete(key);
        this.getUserMenus(uid, key2);
        this.getAllMenus();
        logger.info("reload auth success uid:" + uid);
    }

    /**
     * 获取用户菜单列表
     *
     * @param uid
     * @param key
     * @return
     */
    private JSONArray getUserMenus(int uid, String key) {
        JSONArray array = new JSONArray();
        String sql = "select sa.* from sys_action as sa inner join sys_user_action as sua on sa.id = sua.action_id where sua.uid = ? order by sa.weight asc ";
        List<SysAction> list = BaseDao.dao.queryForListEntity(SysAction.class, sql, uid);
        if (list == null || list.size() < 1) {
            return null;
        }
        for (SysAction sa : list) {
            if (sa.getParentId() < 1) {
                JSONObject json = new JSONObject();
                json.put("title", sa.getName());
                json.put("icon", sa.getIcon());
                JSONArray arr = new JSONArray();
                for (SysAction sa2 : list) {
                    if (sa2.getParentId().equals(sa.getId())) {
                        JSONObject children = new JSONObject();
                        children.put("title", sa2.getName());
                        children.put("icon", sa2.getIcon());
                        children.put("href", sa2.getUrl());
                        arr.add(children);
                    }
                }
                json.put("children", arr);
                array.add(json);
            }
        }
        if (array.size() > 0) {
            redisKit.add(key, 60 * 60, array);
        }
        return array;
    }
}
