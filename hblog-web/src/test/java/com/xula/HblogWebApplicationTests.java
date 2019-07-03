
package com.xula;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.RecordPojo;
import cn.assist.easydao.util.JsonKit;
import com.alibaba.fastjson.JSON;
import com.xula.base.cache.RedisKit;
import com.xula.base.constant.DataSourceConstant;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.JsonBean;
import com.xula.dao.one.IBookMarkMapper;
import com.xula.entity.*;
import com.xula.listener.AsyncComponent;
import com.xula.listener.TestAsync;
import com.xula.service.article.IArticleService;
import com.xula.service.article.impl.ArticleCategoryServiceImpl;
import com.xula.service.bookmark.IBookmarkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HblogWebApplicationTests {

//    @Autowired WRONGTYPE Operation against a key holding the wrong kind of value
//    private JavaMailSender javaMailSender;


//    @Autowired
//    private RedisTemplate redisTemplate;
//
////    @Value("${spring.mail.username}")
////    private String username;
//
////    @Test
////    public void testSendSimple() {
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setFrom(username);
////        message.setTo("xula@yuelinghui.com");
////        message.setSubject("标题：测试标题");
////        message.setText("测试内容部份");
////        javaMailSender.send(message);
////    }
//
//    @Autowired
//    private RedisKit<User> mCacheKit;


//


    @Autowired
    private IBookmarkService iBookmarkService;

    @Test
    public void bookmarkTest() {
        List<BookmarkCategory> bookmarkLists = iBookmarkService.getBookmarkCategoryList();
        System.out.println(JSON.toJSONString(bookmarkLists));
    }
}

