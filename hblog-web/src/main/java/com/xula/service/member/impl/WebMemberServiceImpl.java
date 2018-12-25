package com.xula.service.member.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.constant.SexEnum;
import com.xula.base.constant.TaskConstant;
import com.xula.base.utils.DateUtil;
import com.xula.entity.Member;
import com.xula.entity.MemberGrowLog;
import com.xula.entity.MemberInfo;
import com.xula.entity.extend.MemberDetail;
import com.xula.entity.extend.SignList;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.soap.Addressing;
import java.util.*;
import java.util.stream.Collectors;


/**
 * web项目member 实现类
 * @author xla
 */
@CacheConfig(cacheNames = "cache-time-60")
@Service("IWebMemberService")
public class WebMemberServiceImpl implements IWebMemberService {


    @Autowired
    private IMemberService iMemberService;


    /**
     * 获取用户详情信息
     * @param uid
     * @return
     */
    @Cacheable
    @Override
    public MemberDetail getMemberDetail(int uid) {
        Member member = iMemberService.getMember(uid);
        MemberInfo memberInfo = iMemberService.getMemberInfo(uid);
        if (member == null || memberInfo == null) {
            return null;
        }
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.setUid(uid);
        memberDetail.setAvatar(member.getAvatar());
        memberDetail.setNickname(member.getNickname());
        memberDetail.setSex(SexEnum.getNameById(member.getSex()));
        memberDetail.setCity(member.getCity());
        memberDetail.setCreateTime(member.getCreateTime());
        memberDetail.setSignature(member.getSignature());



        memberDetail.setPointValue(memberInfo.getPointValue());
        memberDetail.setVip(memberInfo.getVip());
        memberDetail.setVipName(memberInfo.getVipName());

        return memberDetail;
    }


    /**
     * 统计签到记录
     * @return
     */
    @Override
    public List<List<SignList>> getSignedList() {

        List<List<SignList>> data = new ArrayList<>();

        // 获取最新的签到人数

        Sort sort = new Sort("create_time", SqlSort.DESC);

        List<SignList> newSinList = getSignList(sort,15);

        // 获取当天签到最快的人数
        Sort sort_1 = new Sort("create_time", SqlSort.ASC);

        List<SignList> quickSinList = getSignList(sort_1,15);

        // 获取签到天数最多的人数
        List<SignList> mostSignList= getMostSignPage(15);

        data.add(newSinList);

        data.add(quickSinList);

        data.add(mostSignList);

        return data;
    }


    /**
     * 获取签到人数
     * @param sort
     * @param pageSize
     * @return
     */
    List<SignList> getSignList(Sort sort, Integer pageSize) {

        StringBuffer sql = new StringBuffer();
        String date = DateUtil.formatYMD(new Date());
        List<Object> params = new ArrayList<>();
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        sql.append("SELECT uid,create_time AS sign_time FROM member_grow_log ");
        sql.append("WHERE type = ? ");
        params.add(TaskConstant.TASK_STATUS_ENABLE);
        sql.append("AND create_time BETWEEN ? AND ? ");
        params.add(startTime);
        params.add(endTime);
        sql.append("GROUP BY uid");
        PagePojo<SignList> page = BaseDao.dao.queryForListPage(SignList.class,sql.toString(),params,sort,1,pageSize);
        return pojoList(page.getPageData());
    }

    /**
     * 获取签到天数排名榜
     * @param pageSize
     * @return
     */
    List<SignList> getMostSignPage(Integer pageSize) {
        Sort sort = new Sort("days",SqlSort.DESC);
        PagePojo<MemberInfo> page = BaseDao.dao.queryForListPage(MemberInfo.class,null,sort,1,pageSize);
        return dealSignList(page.getPageData());
    }

    /**
     * 转化
     * @return
     */
    List<SignList> pojoList(List<SignList> signLists) {
        List<Integer> uids = signLists.stream().map(SignList::getUid).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(signLists)) {
            return new ArrayList<>();
        }
        List<Member> list = BaseDao.dao.queryForListEntity(Member.class,new Conditions("uid",SqlExpr.IN,uids.toArray()));
        Map<Integer,Member> memberMap = list.stream().collect(Collectors.toMap(Member::getUid,m -> m));
        for (SignList signList:signLists) {
            Member member = memberMap.get(signList.getUid());
            if (member != null) {
                signList.setAvatar(member.getAvatar());
                signList.setNickname(member.getNickname());
            }
        }
        return signLists;
    }



    /**
     * 转化
     * @return
     */
    List<SignList> dealSignList(List<MemberInfo> infoList) {
        if (CollectionUtils.isEmpty(infoList)) {
            return new ArrayList<>();
        }
        List<Integer> uids = infoList.stream().map(MemberInfo::getUid).collect(Collectors.toList());
        List<Member> list = BaseDao.dao.queryForListEntity(Member.class,new Conditions("uid",SqlExpr.IN,uids.toArray()));
        Map<Integer,Member> memberMap = list.stream().collect(Collectors.toMap(Member::getUid,m -> m));

        List<SignList> signLists = new ArrayList<>();
        for (MemberInfo memberInfo:infoList) {
            SignList signList = new SignList();
            Member member = memberMap.get(memberInfo.getUid());
            if (member != null) {
                signList.setUid(member.getUid());
                signList.setAvatar(member.getAvatar());
                signList.setNickname(member.getNickname());
                signList.setDays(memberInfo.getDays());
                signLists.add(signList);
            }
        }
        return signLists;
    }

}
