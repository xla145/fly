package com.xula.listener;

import com.xula.base.utils.SpringFactory;
import com.xula.entity.Member;
import com.xula.entity.task.Evolve;
import com.xula.event.EventModel;
import com.xula.event.RegisterEvent;
import com.xula.service.member.IMemberVipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 注册送积分，成长值
 * @author xla
 */
@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    Logger logger = LoggerFactory.getLogger(LoginListener.class);

    private IMemberVipService iMemberVipService = SpringFactory.getBean("IMemberVipService");

    @Async
    @Override
    public void onApplicationEvent(final RegisterEvent event) {
        if(event instanceof RegisterEvent) {
            EventModel eventModel = (EventModel)event.getSource();
            if(eventModel == null){
                return;
            }
            Member member = eventModel.getMember();
            Map<String, Object> param = eventModel.getParam();
            if(member == null || param == null){
                return;
            }
            int uid = member.getUid();
            logger.info("开始注册送积分。。。。。。。。。。。。。。。。。。。。。。。。");
            Evolve evolve = new Evolve();
            evolve.setTaskTag("RegisterTask");
            iMemberVipService.evolveEval(uid,evolve);
            logger.info("完成注册送积分。。。。。。。。。。。。。。。。。。。。。。。。");
        }
    }
}
