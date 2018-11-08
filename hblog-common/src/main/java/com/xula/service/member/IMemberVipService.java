package com.xula.service.member;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import com.xula.entity.MemberPointValues;
import com.xula.entity.task.Evolve;
import com.xula.entity.task.VipGrade;

import java.util.List;


/**
 * 
 * 会员 积分计算 &成长值计算 服务
 *
 * @author xla
 */
public interface IMemberVipService {

	/**
	 * 用户成长评估
	 * 
	 * @param uid 		用户uid
	 * @param evolve	
	 */
	 void evolveEval(int uid, Evolve evolve);

    /**
     * 添加会员成长值
     *
     * @param uid
     * @param operId
     * @param growthValue
     * @param type
     * @param source       
     * @param remark
     */
     boolean addGrowthValue(int uid, int operId, int growthValue, int type, String source, String remark);

    /**
     * 减会员成长值
     *
     * @param uid
     * @param operId
     * @param growthValue
     * @param type
     * @param source
     * @param remark
     */
     boolean deductGrowthValue(int uid, int operId, int growthValue, int type, String source, String remark);

    /**
     * 添加会员积分
     *
     * @param uid
     * @param operId
     * @param pointValue
     * @param type
     * @param source
     * @param remark
     */
     boolean addPointValue(int uid, int operId, int pointValue, int type, String source, String remark);

    /**
     * 减少会员积分
     *
     * @param uid
     * @param operId
     * @param pointValue
     * @param type
     * @param source
     * @param remark
     */
     boolean deductPointValue(int uid, int operId, int pointValue, int type, String source, String remark);

    /**
    *
    * 查询用户积分列表
    *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     */
    PagePojo<MemberPointValues> getPointValueList(int uid, int pageNo, int pageSize);


    /**
    *
    * 查询用户vip等级
    *
    * @param uid
    * @return
    */
    int getMemberVip(int uid);

    /**
     * 获取vip等级
     * @param conditions
     * @return
     */
     List<VipGrade> getVipGradeList(Conditions conditions);

}
