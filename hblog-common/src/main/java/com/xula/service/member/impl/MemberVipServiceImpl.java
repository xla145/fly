package com.xula.service.member.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.xula.base.constant.MemberConstant;
import com.xula.base.constant.TaskConstant;
import com.xula.entity.MemberGrowLog;
import com.xula.entity.MemberInfo;
import com.xula.entity.MemberPointValues;
import com.xula.entity.task.Evolve;
import com.xula.entity.task.VipGrade;
import com.xula.entity.task.VipTaskConf;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IMemberVipService;
import com.xula.service.member.vip.VipTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会员 积分计算 &成长值计算 服务
 *
 * @author xla
 */
@Service("IMemberVipService")
public class MemberVipServiceImpl implements IMemberVipService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMemberService iMemberService;

    /**
     * 添加会员成长值
     *
     * @param uid
     * @param operId
     * @param growthValue
     * @param source
     * @param remark
     */
    @Override
    public boolean addGrowthValue(int uid, int operId, int growthValue,int type, String source, String remark) {
        return savePointValueOrGrowthValue(uid, operId, MemberConstant.MEMBER_GROW_LOG_SYMBOL_INCREASE, type,0, growthValue, source, remark);
    }

    /**
     * 减会员成长值
     *
     * @param uid
     * @param operId
     * @param growthValue
     * @param source
     * @param remark
     */
    @Override
    public boolean deductGrowthValue(int uid, int operId, int growthValue,int type, String source, String remark) {
        return savePointValueOrGrowthValue(uid, operId, MemberConstant.MEMBER_GROW_LOG_SYMBOL_DECREASE, type, 0,growthValue, source, remark);
    }

    /**
     * 添加会员积分
     *
     * @param uid
     * @param operId
     * @param pointValue
     * @param source
     * @param remark
     */
    @Override
    public boolean addPointValue(int uid, int operId, int pointValue,int type, String source, String remark) {
        return savePointValueOrGrowthValue(uid, operId, MemberConstant.MEMBER_GROW_LOG_SYMBOL_INCREASE,type, pointValue, 0, source, remark);
    }

    /**
     * 减少会员成长值
     *
     * @param uid
     * @param operId
     * @param pointValue
     * @param source
     * @param remark
     */
    @Override
    public boolean deductPointValue(int uid, int operId, int pointValue,int type, String source, String remark) {
        return savePointValueOrGrowthValue(uid, operId, MemberConstant.MEMBER_GROW_LOG_SYMBOL_DECREASE,type, pointValue, 0, source, remark);
    }

    /**
     * 获取vip等级信息
     *
     * @param conditions
     * @return
     */
    @Override
    public List<VipGrade> getVipGradeList(Conditions conditions) {
        return BaseDao.dao.queryForListEntity(VipGrade.class, conditions);
    }

    /**
     * 查询用户vip等级
     *
     * @param uid
     * @return
     */
    @Override
    public int getMemberVip(int uid) {
        int vip = 1;
        MemberInfo memberInfo = BaseDao.dao.queryForEntity(MemberInfo.class, uid);
        if (memberInfo != null) {
            vip = memberInfo.getVip();
        }
        return vip;
    }

    /**
     * 查询用户积分列表
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<MemberPointValues> getPointValueList(int uid, int pageNo, int pageSize) {
        Conditions conn = new Conditions("uid", SqlExpr.EQUAL, uid);
        conn.add(new Conditions("point_value", SqlExpr.GT, 0), SqlJoin.AND);
        Sort sort = new Sort("create_time", SqlSort.DESC);
        return BaseDao.dao.queryForListPage(MemberPointValues.class, conn, sort, pageNo, pageSize);
    }

    /**
     * 刷新用户vip等级
     *
     * @param uid
     */
    private void flushMemberVip(int uid) {
        //sum出当前用户的积分 和成长值
        String sql = "SELECT `uid`, SUM(`growth_value`) AS `growth_value`, SUM(`point_value`) AS `point_value`  FROM `member_grow_log` WHERE `uid` = ? AND `symbol` = ? GROUP BY `uid`";
        Map<String, Object> plusMap = BaseDao.dao.queryForMap(sql, uid, MemberConstant.MEMBER_GROW_LOG_SYMBOL_INCREASE);
        Map<String, Object> minusMap = BaseDao.dao.queryForMap(sql, uid, MemberConstant.MEMBER_GROW_LOG_SYMBOL_DECREASE);
        if (plusMap == null || plusMap.size() < 1) {
            logger.warn("[刷新用户vip等级]-[用户没有积分 or 成长值]-uid:" + uid);
            return;
        }
        long growthValue = 0L;
        long pointValue = 0L;
        try {
            growthValue = ((BigDecimal) plusMap.get("growth_value")).longValue();
            pointValue = ((BigDecimal) plusMap.get("point_value")).longValue();
            if (minusMap != null && minusMap.size() > 0) {
                growthValue = growthValue - ((BigDecimal) minusMap.get("growth_value")).longValue();
                pointValue = pointValue - ((BigDecimal) minusMap.get("point_value")).longValue();
            }
            growthValue = growthValue < 0L ? 0 : growthValue;
            pointValue = pointValue < 0L ? 0 : pointValue;
        } catch (Exception e) {
            logger.error("刷新用户vip等级失败:", e);
            return;
        }

        //计算当前成长值对应vip等级
        VipGrade vipGrade = BaseDao.dao.queryForEntity(VipGrade.class, "select * from `vip_grade` where `growth_value` <= ? order by `vip` desc limit 1", growthValue);
        if (vipGrade == null) {
            logger.warn("[刷新用户vip等级]-[等级到达上限]-uid:" + uid + ",growthValue:" + growthValue + ",pointValue:" + pointValue);
            return;
        }

        int vip = vipGrade.getVid();
        String vipName = vipGrade.getName();
        Date date = new Date();

        //更新用户当前等级、积分、成长值相关数据
        StringBuffer flushSql = new StringBuffer("INSERT INTO `member_info`(`uid`,`vip`,`vip_name`,`growth_value`,`point_value`, `create_time`, `update_time`)");
        flushSql.append(" VALUES(?,?,?,?,?,?,?)");
        flushSql.append(" ON DUPLICATE KEY UPDATE `vip` = ?, `vip_name` = ?, `growth_value` = ?, `point_value` = ?, `update_time` = ?");
        int result = BaseDao.dao.insert(flushSql.toString(), uid, vip, vipName, growthValue, pointValue, date, date, vip, vipName, growthValue, pointValue, date);
        logger.info("[刷新用户vip等级]-[更新数据" + (result > 0) + "]-uid:" + uid + ",growthValue:" + growthValue + ",pointValue:" + pointValue);
    }

    /**
     * 将积分/成长值修改记录到MemberGrowLog表中
     *
     * @param uid
     * @param operId
     * @param symbol
     * @param pointValue
     * @param growthValue
     * @param source
     * @param remark
     * @return
     */
    private boolean savePointValueOrGrowthValue(int uid, int operId, int symbol,int type, int pointValue, int growthValue, String source, String remark) {
        Date date = new Date();
        MemberGrowLog mgl = new MemberGrowLog();
        mgl.setUid(uid);
        mgl.setSource(source);
        mgl.setGrowthValue(growthValue);
        mgl.setPointValue(pointValue);
        mgl.setRemark(remark);
        mgl.setOperId(operId);
        mgl.setType(type);
        /**
         * 1：表示添加， 2：表示减少'
         */
        mgl.setSymbol(symbol);
        mgl.setCreateTime(date);
        mgl.setUpdateTime(date);
        int result = BaseDao.dao.insert(mgl);
        if (result == 1) {
            flushMemberVip(uid);
            return true;
        }
        return false;
    }
}
