package com.xula;

import com.alibaba.fastjson.JSON;
import com.xula.base.utils.PageBean;
import com.xula.base.utils.Query;
import com.xula.dao.one.IBookMarkMapper;
import com.xula.dao.one.INavigationMapper;
import com.xula.entity.BookmarkList;
import com.xula.entity.Navigation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
// 设置启用哪个配置文件 ，当有多个配置文件的时候
@ActiveProfiles("dev")
public class HblogCommonApplicationTests {

    @Autowired
    private IBookMarkMapper bookMarkMapper;

    @Autowired
    private INavigationMapper iNavigationMapper;


    @Test
    public void contextLoads() {

        Map map = new HashMap<>();
        map.put("pageNo",1);
        map.put("pageSize",15);


        Query query = new Query(map);


//        PagePojo<SysAction.SysUser> pagePojo  = iSysUserService.getSysUsers(map);
//        System.out.println(JSON.toJSON(pagePojo));

        List<BookmarkList> bookmarkListList = bookMarkMapper.getBookmarkList();
        System.out.println(JSON.toJSON(bookmarkListList));

//        List<Navigation> navigations = iNavigationMapper.queryList(query);
//        int num = iNavigationMapper.queryTotal(query);
//        PageBean pageUtils = new PageBean(navigations,num,15,1);
//        System.out.println(JSON.toJSONString(pageUtils));

//        Navigation navigation = new Navigation();
//        navigation.setCreateTime(new Date());
//        navigation.setUpdateTime(new Date());
//        navigation.setName("test");
//        navigation.setUrl("test");
//        navigation.setWeight(10);
//        int id = iNavigationMapper.saveReturnId(navigation);


//        System.out.println(navigation.getId());

//        Map<String,Object> params = new HashMap<>();
//
//        params.put("status",1);
//
//        List<Navigation> navigations = iNavigationMapper.queryList(params);
//
//        System.out.println(JSON.toJSONString(navigations));
    }

}
