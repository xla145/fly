
package com.xula;

import com.alibaba.fastjson.JSON;
import com.xula.entity.BookmarkCategory;
import com.xula.service.bookmark.IBookmarkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

