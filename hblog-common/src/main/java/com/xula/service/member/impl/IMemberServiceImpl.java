package com.xula.service.member.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import com.xula.base.constant.LoginWayConstant;
import com.xula.base.constant.MemberConstant;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.DateUtil;
import com.xula.base.utils.Md5Utils;
import com.xula.base.utils.RecordBean;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.entity.MemberQq;
import com.xula.entity.MemberWb;
import com.xula.service.member.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 用户服务层实现类
 * @author xla
 */
@Service("IMemberService")
public class IMemberServiceImpl implements IMemberService {

    private  static Logger logger = LoggerFactory.getLogger(IMemberServiceImpl.class);
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


    /**
     * 微博授权注册用户 NoSuchFieldError
     * @param nickname
     * @param uuid
     * @param city
     * @param avatar
     * @param sex
     * @param ip
     * @return
     */
    @Override
    @Transactional
    public RecordBean<Member> registered(String nickname, String uuid, String city, String avatar, String sex, String ip,Integer loginWay) {
        Member member = new Member();
        try {
            Date now = new Date();
            member.setNickname(nickname);
            // 第三方授权登录的，设置一个10位数随机密码
            String pwd = Md5Utils.md5(CommonUtil.getStringRandom(10));
            member.setPassword(pwd);
            member.setCreateTime(now);
            member.setUpdateTime(now);
            member.setChannelId(loginWay);
            member.setPlatform(LoginWayConstant.getDescribe(loginWay));
            member.setLastTime(now);
            member.setCity(city);
            member.setAvatar(avatar);
            member.setIp(ip);
            member.setAvatar(avatar);
            member.setStatus(MemberConstant.USET_EMAIL_UNACTIVE);

            int uid = BaseDao.dao.insertReturnId(member);
            if (uid == -1) {
                throw new Exception("用户注册失败！");
            }

            if (MemberConstant.CHANNEL_BY_WEIBO == loginWay.intValue()) {
                MemberWb memberWb = new MemberWb();
                memberWb.setUid(uid);
                memberWb.setUuid(uuid);
                RecordBean<MemberWb> recordBean = createMemberWb(memberWb);
                if (!recordBean.isSuccessCode()) {
                    throw new Exception("微博绑定失败！");
                }
            }

            if (MemberConstant.CHANNEL_BY_QQ == loginWay.intValue()) {
                MemberQq memberQq = new MemberQq();
                memberQq.setUid(uid);
                memberQq.setUuid(uuid);
                RecordBean<MemberQq> recordBean = createMemberQq(memberQq);
                if (!recordBean.isSuccessCode()) {
                    throw new Exception("QQ绑定失败！");
                }
            }

            MemberInfo memberInfo = new MemberInfo();

            memberInfo.setUid(uid);
            memberInfo.setVip(1);
            memberInfo.setVipName("普通会员");
            memberInfo.setGrowthValue(0L);
            memberInfo.setPointValue(0L);
            memberInfo.setCreateTime(now);
            memberInfo.setUpdateTime(now);
            member.setUid(uid);
            int result = BaseDao.dao.insert(memberInfo);

            if (result != 1) {
                throw new Exception("用户注册失败！");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("用户注册失败!reason:{}",e.getMessage());
            return RecordBean.error("用户注册异常！");
        }
        return RecordBean.success("success",member);
    }

    /**
     * qq授权注册用户
     * @param openId
     * @param nickname
     * @param uuid
     * @param city
     * @param avatar
     * @param sex
     * @param ip
     * @param loginWay
     * @return
     */
    @Override
    public RecordBean<Member> registered(String openId, String nickname, String uuid, String city, String avatar, String sex, String ip, Integer loginWay) {
        Member member = new Member();
        try {
            Date now = new Date();
            member.setNickname(nickname);
            // 第三方授权登录的，设置一个10位数随机密码
            String pwd = Md5Utils.md5(CommonUtil.getStringRandom(10));
            member.setPassword(pwd);
            member.setCreateTime(now);
            member.setUpdateTime(now);
            member.setChannelId(loginWay);
            member.setPlatform(MemberConstant.channelWay.get(loginWay));
            member.setLastTime(now);
            member.setCity(city);
            member.setAvatar(avatar);
            member.setIp(ip);
            member.setAvatar(avatar);
            member.setStatus(MemberConstant.USET_EMAIL_UNACTIVE);

            int uid = BaseDao.dao.insertReturnId(member);
            if (uid == -1) {
                throw new Exception("用户注册失败！");
            }

            MemberWb memberWb = new MemberWb();

            memberWb.setUid(uid);
            memberWb.setUuid(uuid);

            RecordBean<MemberWb> recordBean = createMemberWb(memberWb);

            if (!recordBean.isSuccessCode()) {
                throw new Exception("微博绑定失败！");
            }

            MemberInfo memberInfo = new MemberInfo();

            memberInfo.setUid(uid);
            memberInfo.setVip(1);
            memberInfo.setVipName("普通会员");
            memberInfo.setGrowthValue(0L);
            memberInfo.setPointValue(0L);
            memberInfo.setCreateTime(now);
            memberInfo.setUpdateTime(now);
            member.setUid(uid);
            int result = BaseDao.dao.insert(memberInfo);

            if (result != 1) {
                throw new Exception("用户注册失败！");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("用户注册失败!reason:{}",e.getMessage());
            return RecordBean.error("用户注册异常！");
        }
        return RecordBean.success("success",member);
    }

    /**
     * 创建微博授权信息
     * @param memberWb
     * @return
     */
    @Override
    public RecordBean<MemberWb> createMemberWb(MemberWb memberWb) {
        Date now = new Date();
        memberWb.setCreateTime(now);
        memberWb.setUpdateTime(now);
        int result = BaseDao.dao.insert(memberWb);
        if (result != 1) {
            return RecordBean.error("微博绑定失败");
        }
        return RecordBean.success("success");
    }



    /**
     * 创建QQ授权信息
     * @param memberQq
     * @return
     */
    @Override
    public RecordBean<MemberQq> createMemberQq(MemberQq memberQq) {
        Date now = new Date();
        memberQq.setCreateTime(now);
        memberQq.setUpdateTime(now);
        int result = BaseDao.dao.insert(memberQq);
        if (result != 1) {
            return RecordBean.error("qq绑定失败");
        }
        return RecordBean.success("success");
    }

    @Override
    public RecordBean<MemberWb> updateMemberWb(MemberWb memberWb) {
        return null;
    }


    /**
     * 获取用户信息 授权 ambiguous
     * @param uuid
     * @return
     */
    @Override
    public Member getMemberByUuid(String uuid,Integer loginWay) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT m.uid,m.nickname ");
        sql.append("FROM member m ");
        if (MemberConstant.CHANNEL_BY_QQ == loginWay.intValue()) {
            sql.append("JOIN member_qq b ON(m.uid = b.uid) ");
            sql.append("WHERE b.uuid = ?");
        }
        if (MemberConstant.CHANNEL_BY_WEIBO == loginWay.intValue()) {
            sql.append("JOIN member_weibo b ON(m.uid = b.uid) ");
            sql.append("WHERE b.uuid = ?");
        }
        return BaseDao.dao.queryForEntity(Member.class,sql.toString(),uuid);
    }
}
