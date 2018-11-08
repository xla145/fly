package com.xula.service.member.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import com.xula.base.constant.MemberConstant;
import com.xula.base.utils.Md5Utils;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.service.member.IMemberService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Date;

/**
 * 用户服务层实现类
 * @author xla
 */
@Service("IMemberService")
public class IMemberServiceImpl implements IMemberService {


    /**
     * 注册
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
        int result = BaseDao.dao.insert(member);
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

        return RecordBean.success("success!");
    }
}
