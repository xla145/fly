package com.xula.entity.extend;

import com.xula.entity.ArticleComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章列表
 * @author xla
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CommentList extends ArticleComment {


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
     * vip名称
     */
    private String vipName;

    /**
     * 判断用户是否点赞
     */
    private boolean love = false;


    public boolean getIsLove() {
        return love;
    }
}
