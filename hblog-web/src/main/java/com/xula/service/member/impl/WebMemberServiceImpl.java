package com.xula.service.member.impl;

import com.xula.base.constant.SexEnum;
import com.xula.entity.Member;
import com.xula.entity.MemberInfo;
import com.xula.entity.extend.MemberDetail;
import com.xula.service.member.IMemberService;
import com.xula.service.member.IWebMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;


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
}
