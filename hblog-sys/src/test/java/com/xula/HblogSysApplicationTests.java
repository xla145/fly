package com.xula;

import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.xula.entity.SysAction;
import com.xula.service.auth.ISysRoleService;
import com.xula.service.sys.sysuser.ISysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HblogSysApplicationTests {

    @Autowired
    private ISysUserService iSysUserService;


    @Test
    public void contextLoads() {

        Map map = new HashMap<>();
        map.put("pageNo",1);
        map.put("pageSize",15);
        PagePojo<SysAction.SysUser> pagePojo  = iSysUserService.getSysUsers(map);
        System.out.println(JSON.toJSON(pagePojo));
    }

}
