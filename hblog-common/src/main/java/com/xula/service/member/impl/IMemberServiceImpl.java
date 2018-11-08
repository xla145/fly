package com.xula.service.member.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import com.xula.base.constant.MemberConstant;
import com.xula.base.utils.Md5Utils;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.service.member.IMemberService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务层实现类
 * @author xla
 */
@Service("IMemberService")
public class IMemberServiceImpl implements IMemberService {


    /**
     * 注册送积分
     * @param nickname
     * @param password
     * @param email
     * @return
     */
    @Override
    public RecordBean<Member> registered(String nickname, String password, String email) {
        Conditions conn = new Conditions("email", SqlExpr.EQUAL,email);
        Member member1 =  BaseDao.dao.queryForEntity(Member.class,conn);
        if (member1 != null) {
            return RecordBean.error("该邮箱已经被注册！");
        }
        String passwd = Md5Utils.md5(password);
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(passwd);
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        member.setChannelId(MemberConstant.CHANNEL_SELF);
        member.setPlatform("网站");
        member.setLastTime(new Date());
        int uid = BaseDao.dao.insertReturnId(member);
        if (uid == -1) {
            return RecordBean.error("用户注册失败！");
        }

        MemberInfo memberInfo = new MemberInfo();

        memberInfo.setUid(uid);
        memberInfo.setVip(1);
        memberInfo.setVipName("普通会员");
        memberInfo.setGrowthValue(0L);
        memberInfo.setPointValue(0L);
        memberInfo.setCreateTime(new Date());
        memberInfo.setUpdateTime(new Date());

        member.setUid(uid);
        int result = BaseDao.dao.insert(memberInfo);

        if (result != 1) {
            return RecordBean.error("用户注册失败！");
        }
        return RecordBean.success("用户注册成功！",member);
    }


    /**
     * 用户登录
     * @param password
     * @param email
     * @return
     */
    @Override
    public RecordBean<Member> login(String password, String email) {

        String passwd = Md5Utils.md5(password);

        Conditions conn = new Conditions("email", SqlExpr.EQUAL,email);

        Member member =  BaseDao.dao.queryForEntity(Member.class,conn);

        if (member == null) {
            return RecordBean.error("账号或密码错误！");
        }
        if (MemberConstant.USET_DALID_YES != member.getStatus().intValue()) {
            return RecordBean.error("账号或密码错误！");
        }

        if (!passwd.equals(member.getPassword())) {
            return RecordBean.error("账号或密码错误！");
        }

        return RecordBean.success("success!",member);
    }


    /**
     * 获取用户基本信息
     * @param uid
     * @return
     */
    @Override
    public MemberInfo getMemberInfo(int uid) {
        Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
        return BaseDao.dao.queryForEntity(MemberInfo.class,conn);
    }

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @Override
    public Member getMember(int uid) {
        Conditions conn = new Conditions("uid",SqlExpr.EQUAL,uid);
        return BaseDao.dao.queryForEntity(Member.class,conn);
    }
}
