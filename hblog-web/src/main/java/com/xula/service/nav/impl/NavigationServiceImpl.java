package com.xula.service.nav.impl;

import com.xula.base.constant.NavigationConstant;
import com.xula.dao.one.INavigationMapper;
import com.xula.entity.Navigation;
import com.xula.service.nav.INavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xula
 * @date 2019/07/04 14:18
 **/
@Service("INavigationService")
public class NavigationServiceImpl implements INavigationService {


    @Autowired
    private INavigationMapper iNavigationMapper;


    @Override
    public List<Navigation> getNavigationList() {
        Map<String,Object> params = new HashMap<>(1);
        params.put("status", NavigationConstant.NAV_STATUS_DISABLE);
        return iNavigationMapper.queryList(params);
    }
}
