package com.xula.service.member.vip;

import com.alibaba.fastjson.JSON;
import com.xula.base.constant.TaskConstant;
import com.xula.entity.task.Evolve;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 注册送积分
 * @author xla
 */
public class RegisterTask extends VipTask{

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String CURRENT_TASK = "registerTask";

	@Override
	public void run(int uid, String taskName, String param, Evolve evolve) {
		if (evolve == null || StringUtils.isBlank(param)) {
			logger.info("[会员完成注册任务]-[参数为空],uid:" + uid + ",param:" + param + ",evolve：" + JSON.toJSONString(evolve));
			return;
		}
		if(!CURRENT_TASK.equalsIgnoreCase(evolve.getTaskTag())){
			logger.info("[会员完成注册任务]-[订单不符合条件]:uid:" + uid + ",param:" + param);
			return;
		}

		/**
         * 分割积分和成长值
		 */
		String[] params = param.split(";");

		if (params == null || params.length != 2) {
			logger.info("[会员完成注册任务]-[订单不符合条件]:uid:" + uid + ",param:" + param);
			return;
		}

		int pointValue = Integer.valueOf(params[0]).intValue();
		int growthValue = Integer.valueOf(params[1]).intValue();

		//记录日志member_grow_log 表
		if (growthValue > 0) {
			boolean gResult = super.addGrowthValue(uid, growthValue, TaskConstant.TASK_TYPE_REG, taskName, "完成注册， 增加成长值" + growthValue + "; uid：" + uid);
			logger.info("[会员完成注册任务]-[记录用户成长值:" + gResult + "],uid:" + uid + ",param:" + param + ",growthValue：" + growthValue + ",uid：" + uid);
		}
		if (pointValue > 0) {
			boolean pResult = super.addPointValue(uid, pointValue,TaskConstant.TASK_TYPE_REG, taskName, "完成注册， 增加积分" + pointValue + "; uid：" + uid);
			logger.info("[会员完成注册任务]-[记录用户积分:" + pResult + "],uid:" + uid + ",param:" + param + ",pointValue：" + pointValue + ",uid：" + uid);
		}
		
	}

}
