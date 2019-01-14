package com.xula.service.member.impl;

import cn.assist.easydao.common.*;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.constant.MemberConstant;
import com.xula.base.constant.SexEnum;
import com.xula.base.constant.TaskConstant;
import com.xula.base.utils.DateUtil;
import com.xula.base.utils.Md5Utils;
import com.xula.base.utils.RecordBean;
import com.xula.entity.*;
import com.xula.entity.extend.ArticleList;
import com.xula.entity.extend.MemberDetail;
import com.xula.entity.extend.SignList;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
        memberDetail.setEmail(member.getEmail());

        memberDetail.setPointValue(memberInfo.getPointValue());
        memberDetail.setVip(memberInfo.getVip());
        memberDetail.setVipName(memberInfo.getVipName());


        // 判断用户是否绑定qq或微博
        MemberQq memberQq = getMemberQq(uid);
        if (memberQq != null) {
            memberDetail.setQQ(true);
        }
        MemberWb memberWb = getMemberWb(uid);
        if (memberWb != null) {
            memberDetail.setWeiBo(true);
        }
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

    /**
     * 更新头像
     * @param avatar
     * @param uid
     * @return
     */
    @Override
    public RecordBean<String> updateAvatar(String avatar, Integer uid) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE member SET avatar = ?  WHERE uid = ? ");
        int result = BaseDao.dao.update(sql.toString(),avatar,uid);
        if (result == 0) {
            return RecordBean.error("更新头像失败！");
        }
        return RecordBean.success("更新头像成功！");
    }


    /**
     * 更新密码
     * @param nowPwd
     * @param pwd
     * @param uid
     * @return
     */
    @Override
    public RecordBean<String> updatePwd(String nowPwd, String pwd, Integer uid) {
        Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
        conn.add(new Conditions("password",SqlExpr.EQUAL,Md5Utils.md5(nowPwd)),SqlJoin.AND);
        Member member = BaseDao.dao.queryForEntity(Member.class,conn);
        if (member == null) {
            return RecordBean.error("当前密码有误！");
        }
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE member SET password = ?  WHERE uid = ? ");
        int result = BaseDao.dao.update(sql.toString(),Md5Utils.md5(pwd),uid);
        if (result == 0) {
            return RecordBean.error("更新密码失败！");
        }
        return RecordBean.success("更新密码成功！");
    }


    /**
     * 获取qq授权信息
     * @param uid
     * @return
     */
    @Override
    public MemberQq getMemberQq(int uid) {
        Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
        conn.add(new Conditions("status",SqlExpr.EQUAL, MemberConstant.STATUS_BINDED),SqlJoin.AND);
        return BaseDao.dao.queryForEntity(MemberQq.class,conn);
    }


    /**
     * 获取微博授权信息
     * @param uid
     * @return
     */
    @Override
    public MemberWb getMemberWb(int uid) {
        Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
        conn.add(new Conditions("status",SqlExpr.EQUAL, MemberConstant.STATUS_BINDED),SqlJoin.AND);
        return BaseDao.dao.queryForEntity(MemberWb.class,conn);
    }


    /**
     * 更新用户信息
     * @param member
     * @return
     */
    @CacheEvict
    @Override
    public RecordBean<Member> updateMember(Member member) {
        int result = BaseDao.dao.update(member);
        if (result == 0) {
            return RecordBean.error("更新失败！");
        }
        return RecordBean.success("更新失败！");
    }

    /**
     * 获取用户收藏的文章
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Cacheable
    @Override
    public PagePojo<MemberArticle> getMemberArticlePage(Conditions conn, Integer pageNo, Integer pageSize) {
        Sort sort = new Sort("create_time",SqlSort.DESC);
        return BaseDao.dao.queryForListPage(MemberArticle.class,conn,sort,pageNo,pageSize);
    }

    /**
     *
     * @param conn
     * @return
     */
    @Override
    public List<MemberMessage> getMemberMessageList(Conditions conn) {
        conn.add(new Conditions("status",SqlExpr.UNEQUAL,-1),SqlJoin.AND);
        return BaseDao.dao.queryForListEntity(MemberMessage.class,conn);
    }

    /**
     *
     * @param id
     * @param uid
     * @param isAll
     * @return
     */
    @Override
    public RecordBean<String> delMemberMessage(Integer id, Integer uid, boolean isAll) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sql.append("UPDATE member_message SET status = -1 WHERE to_uid = ? ");
        params.add(uid);
        if (!isAll) {
            sql.append("AND id = ?");
            params.add(id);
        }
        int result = BaseDao.dao.update(sql.toString(),params.toArray());
        if (result == 0) {
            return RecordBean.error("删除失败！");
        }
        return RecordBean.success("删除成功！");
    }
}
