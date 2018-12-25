package com.xula.service.member.vip;

import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.RecordPojo;
import com.alibaba.fastjson.JSON;
import com.xula.base.constant.TaskConstant;
import com.xula.base.utils.DateUtil;
import com.xula.entity.task.Evolve;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 签到送积分
 * @author xla
 */
@Component("SignTask")
public class SignTask extends VipTask{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(int uid, String taskName, String param, Evolve evolve) {
		if (evolve == null || StringUtils.isBlank(param)) {
			logger.info("[会员完成签到任务]-[参数为空],uid:" + uid + ",param:" + param + ",evolve：" + JSON.toJSONString(evolve));
			return;
		}

		/**
         * 分割积分和成长值
		 */
		String[] params = param.split(";");

		if (params == null || params.length != 2) {
			logger.info("[会员完成签到任务]:uid:" + uid + ",param:" + param);
			return;
		}

		int pointValue = Integer.valueOf(params[0]).intValue();
		int growthValue = Integer.valueOf(params[1]).intValue();

		//记录日志member_grow_log 表
		if (growthValue > 0) {
			boolean gResult = super.addGrowthValue(uid, growthValue, TaskConstant.TASK_TYPE_SIGN, taskName, "完成签到， 增加成长值" + growthValue + "; uid：" + uid);
			logger.info("[会员完成签到任务]-[记录用户成长值:" + gResult + "],uid:" + uid + ",param:" + param + ",growthValue：" + growthValue + ",uid：" + uid);
		}
		if (pointValue > 0) {
			boolean pResult = super.addPointValue(uid, pointValue,TaskConstant.TASK_TYPE_SIGN, taskName, "完成签到， 增加积分" + pointValue + "; uid：" + uid);
			logger.info("[会员完成签到任务]-[记录用户积分:" + pResult + "],uid:" + uid + ",param:" + param + ",pointValue：" + pointValue + ",uid：" + uid);
		}


		// 更新签到天数 昨天
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATE_FORMAT(create_time,'%Y-%m-%d') create_time,type,uid FROM member_grow_log WHERE uid = ? AND type = ? ");
		sql.append("HAVING create_time = ?");
		Date yesterday = DateUtil.getDateAddOffSet(Calendar.DATE,-1);
		String date = DateUtil.formatYMD(yesterday);
		List<RecordPojo> recordPojo = BaseDao.dao.queryList(sql.toString(),uid,TaskConstant.TASK_TYPE_SIGN,date);

		StringBuffer sql_1 = new StringBuffer();
		if (!CollectionUtils.isEmpty(recordPojo)) {
			// 表示昨天有签到过
			sql_1.append("UPDATE member_info SET days = days + 1 ");
		} else {
			// 否则重新计算
			sql_1.append("UPDATE member_info SET days = 1 ");
		}
		sql_1.append("WHERE uid = ?");
		BaseDao.dao.update(sql_1.toString(),uid);
	}
}
