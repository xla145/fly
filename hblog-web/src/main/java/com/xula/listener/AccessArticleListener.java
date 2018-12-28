package com.xula.listener;

import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.ArticleBrowse;
import com.xula.event.AccessArticleEvent;
import com.xula.event.EventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 添加浏览文章的记录
 * @author xla
 */
@Component
public class AccessArticleListener implements ApplicationListener<AccessArticleEvent> {

    Logger logger = LoggerFactory.getLogger(LoginListener.class);

    @Async
    @Override
    public void onApplicationEvent(final AccessArticleEvent event) {
        if(event instanceof AccessArticleEvent) {
            EventModel eventModel = (EventModel)event.getSource();
            if(eventModel == null){
                return;
            }
            Map<String,Object> param = eventModel.getParam();
            String aid = param.get("aid").toString();
            Integer uid = Integer.parseInt(param.get("uid").toString());
            // 只记录真实用户
            if (uid != -1) {
                ArticleBrowse articleBrowse = new ArticleBrowse();
                articleBrowse.setArticleId(param.get("aid").toString());
                articleBrowse.setCreateTime(new Date());
                articleBrowse.setUid(uid);
                articleBrowse.setIp(param.get("ip").toString());
                articleBrowse.setRemark("用户访问文章。。。。。");
                int result = BaseDao.dao.insert(articleBrowse);
                if (result != 1) {
                    logger.error("添加用户浏览文章记录失败！");
                    return;
                }
            }
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE article SET browse = browse + 1 WHERE aid = ?");
            BaseDao.dao.update(sql.toString(),aid);
        }
    }
}
