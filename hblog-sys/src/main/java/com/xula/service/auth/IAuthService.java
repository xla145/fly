package com.xula.service.auth;


import com.alibaba.fastjson.JSONArray;
import com.xula.entity.SysAction;

import java.util.List;


/**
 * 用户操作认证
 *
 * @author gull
 */
public interface IAuthService {

    /**
     * 获取所有菜单
     * @return
     */
    JSONArray getAllMenus();

    /**
     * 获取用户操作菜单
     *
     * @param uid
     * @return
     */
     JSONArray getMenu(int uid);

    /**
     * 重新加载用户权限
     *
     * @param uid
     */
     void reload(int uid);




}
