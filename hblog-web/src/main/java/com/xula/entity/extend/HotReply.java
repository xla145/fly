package com.xula.entity.extend;

import lombok.Data;

/**
 * 热门回帖用户信息
 * @author xla
 */
@Data
public class HotReply {

    /**
     * 用户uid
     */
    private  Integer uid;

    /**
     * 头像
     */
    private  String avatar;

    /**
     * 昵称
     */
    private  String nickname;

    /**
     * 链接
     */
    private  String url;

    /**
     * 回答数
     */
    private  Integer replyNum;

}
