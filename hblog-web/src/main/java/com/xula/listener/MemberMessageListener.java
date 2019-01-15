package com.xula.listener;

import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.ArticleBrowse;
import com.xula.entity.MemberMessage;
import com.xula.event.AccessArticleEvent;
import com.xula.event.EventModel;
import com.xula.event.MemberMessageEvent;
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
public class MemberMessageListener implements ApplicationListener<MemberMessageEvent> {

    Logger logger = LoggerFactory.getLogger(LoginListener.class);

    @Async
    @Override
    public void onApplicationEvent(final MemberMessageEvent event) {
        if(event instanceof MemberMessageEvent) {
            EventModel eventModel = (EventModel)event.getSource();
            if(eventModel == null){
                return;
            }
            Map<String,Object> param = eventModel.getParam();
            String aid = param.get("aid").toString();
            Integer commentId = Integer.parseInt(param.get("commentId").toString());
            String content = param.get("content").toString();
            Integer uid = Integer.parseInt(param.get("uid").toString());
            Integer toUid = Integer.parseInt(param.get("toUid").toString());


            MemberMessage memberMessage = new MemberMessage();
            memberMessage.setArticleId(aid);
            memberMessage.setCommentId(commentId);
            memberMessage.setContent(content);
            memberMessage.setFromUid(uid);
            memberMessage.setToUid(toUid);
            memberMessage.setCreateTime(new Date());
            memberMessage.setUpdateTime(new Date());

            BaseDao.dao.insert(memberMessage);

        }
    }
}
