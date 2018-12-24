
package com.xula;

import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.RecordPojo;
import cn.assist.easydao.util.JsonKit;
import com.xula.base.cache.RedisKit;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.JsonBean;
import com.xula.entity.User;
import com.xula.service.article.IArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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

    @Autowired
    private IArticleService iArticleService;

    @Test
    public void test() throws InterruptedException {

//        List<MemberInfo> list = new ArrayList<>();
//
//        MemberInfo member = new MemberInfo();
//
//        member.setGrowthValue((long) 33);
//
//        member.setVip(1);
//
//        member.setVipName("vip");
//
//        member.setUid(3);
//
//        member.setCreateTime(new Date());
//
//        member.setUpdateTime(new Date());
//
//        MemberInfo member1 = new MemberInfo();
//
//        member1.setGrowthValue((long) 34);
//
//        member1.setVip(14);
//
//        member1.setVipName("vip4");
//
//        member1.setUid(8);
//
//        member1.setPointValue((long) 4400);
//
//        member1.setCreateTime(new Date());
//
//        member1.setUpdateTime(new Date());
//
//
//        list.add(member);
//        list.add(member1);


//        MemberInfo member = new MemberInfo();
//
//        member.setGrowthValue((long) 33);
//
//        member.setVip(1);
//
//        member.setVipName("vipName");
//
//        member.setUid(3);
//
//        member.setCreateTime(new Date());
//
//        member.setUpdateTime(new Date());

//        BaseDao.dao.merge(member,"vipName");


//        List<Article> articleList = iArticleService.getArticleList(new Conditions());
//
//
//        PagePojo<ArticleList> pagePojo = iArticleService.getArticlePage(new Conditions(),1,10);





//        MemberInfo member = new MemberInfo();
//
//        member.setGrowthValue((long) 33);
//
//        member.setVip(1);
//
//        member.setVipName("vipName");
//
//        member.setUid(100);
//
//        member.setCreateTime(new Date());
//
//        member.setUpdateTime(new Date());




        List<RecordPojo> recordPojo = BaseDao.dao.queryList("SELECT * FROM member");



        System.out.println(JsonBean.success("success", JsonKit.toJson(recordPojo)));
    }


}

