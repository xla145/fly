package com.xula.service.member.vip;


import com.xula.entity.task.Evolve;
import com.xula.service.member.impl.MemberVipServiceImpl;

/**
 * 
 * 会员任务
 *
 * @author xla
 */
public abstract class VipTask extends MemberVipServiceImpl {

	/**
	 * 用户成长评估
	 * 
	 * @param uid
	 * @param taskName
	 * @param param
	 * @param evolve
	 */
	public abstract void run(int uid, String taskName, String param, Evolve evolve);

	
	/**
	 * 
	 * 向上取整计算
	 * 
	 * @param originalVal
	 * @param factor
	 * @return
	 */
	public int compute(double originalVal, float factor){
		/**
		 * 向上取整
		 */
		int ov = (int)Math.ceil(originalVal);
        /**
         * 省掉小数部分
		 */
		int val = (int) (factor * ov);
		return val;
	}
	
	/**
	 * 添加会员成长值
	 * 
	 * @param uid
	 * @param growthValue
	 * @param remark
	 */
	public boolean addGrowthValue(int uid, int growthValue,int type,String taskName, String remark){
		return super.addGrowthValue(uid, -1, growthValue,type, taskName, remark);
	}
	
	/**
	 * 添加会员积分
	 * 
	 * @param uid
	 * @param pointValue
	 * @param remark
	 */
	public boolean addPointValue(int uid, int pointValue,int type,String taskName, String remark){
		return super.addPointValue(uid, -1, pointValue,type, taskName, remark);
	}
}
