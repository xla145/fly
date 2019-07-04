package com.xula.service.nav;

import com.xula.entity.Navigation;

import java.util.List;

/**
 * @author xula
 * @date 2019/07/04 14:16
 **/
public interface INavigationService {

    /**
     * 获取导航栏列表
     * @return
     */
    List<Navigation> getNavigationList();

}
