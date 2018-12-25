package com.xula.service.task;

import cn.assist.easydao.dao.BaseDao;
import com.alibaba.fastjson.JSON;
import com.xula.base.constant.TaskConstant;
import com.xula.base.utils.SpringFactory;
import com.xula.entity.MemberInfo;
import com.xula.entity.task.Evolve;
import com.xula.entity.task.VipTaskConf;
import com.xula.service.member.IMemberService;
import com.xula.service.member.vip.VipTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务执行工厂
 * @author xla
 */

@Component("WebTaskFactory")
public class WebTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(WebTaskFactory.class);

    @Autowired
    private IMemberService iMemberService;

    /**
     * 用户成长评估
     *
     * @param uid
     */
    public void evolveEval(int uid, Evolve evolve) {
        int vip = 1;
        MemberInfo memberInfo = iMemberService.getMemberInfo(uid);
        if (memberInfo != null) {
            vip = memberInfo.getVip();
        }
        List<VipTaskConf> vts = getVipTasks(vip);

        logger.info("[会员成长]--uid:" + uid + ",user vip:" + vip + ", vip task:" + JSON.toJSONString(vts));

        if (vts == null) {
            return;
        }
        Map<String,VipTaskConf> map = vts.stream().collect(Collectors.toMap(VipTaskConf::getClazzName, a -> a));

        VipTaskConf vt = map.get(evolve.getTaskTag());
        String className = vt.getClazzName();
        String param = vt.getParam();
        VipTask vipTask = SpringFactory.getBean(className);
        String taskName = vt.getName();
        vipTask.run(uid, taskName, param, evolve);
    }

    /**
     * 获取有效的vip等级任务
     *
     * @param vip
     * @return
     */
    private List<VipTaskConf> getVipTasks(int vip) {
        String sql = "SELECT a.param, b.name, b.clazz_name FROM `vip_grade_task` AS a INNER JOIN `vip_task` AS b ON a.`vtid` = b.`vtid` WHERE a.`vip` = ? AND b.`status` = ?";
        return BaseDao.dao.queryForListEntity(VipTaskConf.class, sql, vip, TaskConstant.TASK_STATUS_ENABLE);
    }



}
