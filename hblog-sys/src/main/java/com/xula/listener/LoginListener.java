package com.xula.listener;

import cn.assist.easydao.dao.BaseDao;
import com.xula.entity.SysAction;
import com.xula.event.EventModel;
import com.xula.event.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 登录成功事件处理
 *
 * @author caibin
 */
@EnableAsync
@Component
public class LoginListener implements ApplicationListener<LoginEvent> {

    Logger logger = LoggerFactory.getLogger(LoginListener.class);


    @Async
    @Override
    public void onApplicationEvent(final LoginEvent event) {
        if (event instanceof LoginEvent) {
            EventModel eventModel = (EventModel) event.getSource();
            if (eventModel == null) {
                return;
            }
            SysAction.SysUser sysUser = eventModel.getSysUser();
            Map<String, Object> param = eventModel.getParam();
            if (sysUser == null || param == null) {
                return;
            }
            int uid = sysUser.getUid();
            Date lastLoginTime = (Date) param.get("lastLoginTime");
            //更新登录时间
            int i = BaseDao.dao.update("update sys_user set last_login_time = ? where uid = ?", lastLoginTime, uid);
            if (i < 1) {
                logger.warn("更新用户最后登录时间失败,uid:" + uid + ", 最后登录时间:" + lastLoginTime);
            }
        }
    }
}
