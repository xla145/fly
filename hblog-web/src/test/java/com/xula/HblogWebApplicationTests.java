
package com.xula;

import com.xula.base.cache.RedisKit;
import com.xula.base.utils.CommonUtil;
import com.xula.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HblogWebApplicationTests {

//    @Autowired WRONGTYPE Operation against a key holding the wrong kind of value
//    private JavaMailSender javaMailSender;


    @Autowired
    private RedisTemplate redisTemplate;

//    @Value("${spring.mail.username}")
//    private String username;

//    @Test
//    public void testSendSimple() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(username);
//        message.setTo("xula@yuelinghui.com");
//        message.setSubject("标题：测试标题");
//        message.setText("测试内容部份");
//        javaMailSender.send(message);
//    }

    @Autowired
    private RedisKit<User> mCacheKit;


    @Test
    public void contextLoads() throws InterruptedException {
        String key = CommonUtil.getId("Test");
        User user = new User();
        user.setCode("eeeeeeee");
        user.setEmail("13455555555");


        redisTemplate.boundValueOps(key).set(user,10000, TimeUnit.SECONDS);

        System.out.println(redisTemplate.boundValueOps(key).get());

        Thread.sleep(100);

        System.out.println(redisTemplate.boundValueOps(key).get());

    }


    @Test
    public void contextLoads_1() throws InterruptedException {

        String key = CommonUtil.getId("Test");
        User user = new User();
        user.setCode("eeeeeeee");
        user.setEmail("13455555555");
        mCacheKit.add(key,10,user);

        System.out.println(mCacheKit.get(key));

        Thread.sleep(10000);

        System.out.println(redisTemplate.boundValueOps(key).get());
    }

}

