package com.xula.listener;

import com.xula.base.utils.SpringFactory;
import com.xula.entity.Member;
import com.xula.entity.task.Evolve;
import com.xula.event.EventModel;
import com.xula.event.TaskEvent;
import com.xula.service.member.IMemberVipService;
import com.xula.service.task.WebTaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 任务送积分，统一接听类
 * @author xla
 */
@Component
@Import(SpringFactory.class)
public class TaskListener implements ApplicationListener<TaskEvent> {

    Logger logger = LoggerFactory.getLogger(LoginListener.class);

    @Async
    @Override
    public void onApplicationEvent(final TaskEvent event) {
        WebTaskFactory webTaskFactory = SpringFactory.getBean("WebTaskFactory");
        if(event instanceof TaskEvent) {
            EventModel eventModel = (EventModel)event.getSource();
            if(eventModel == null){
                return;
            }
            Member member = eventModel.getMember();
            Map<String, Object> param = eventModel.getParam();
            if(member == null || param == null){
                return;
            }
            String taskCode = param.get("taskCode").toString();
            int uid = member.getUid();
            logger.info("开始送积分。。。。。。。。。。。。。。。。。。。。。。。。");
            Evolve evolve = new Evolve();
            evolve.setTaskTag(taskCode);
            webTaskFactory.evolveEval(uid,evolve);
            logger.info("完成送积分。。。。。。。。。。。。。。。。。。。。。。。。");
        }
    }
}
