package com.xula.service.article;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.extend.CommentList;

import java.util.List;

/**
 * 评论服务层
 * @author xla
 */
public interface ICommentService {


    /**
     * 评论
     * @param aid
     * @param content
     * @return
     */
    RecordBean<String> reply(String aid,String content);


    /**
     * 分页获取文章评论列表
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    PagePojo<CommentList> getCommentPage(Conditions conn, Integer pageNo, Integer pageSize);


    /**
     * 点赞
     * @param commentId
     * @param fever 热度
     * @return
     */
    RecordBean<String> love(Integer commentId,Integer fever);


    /**
     * 评论采纳
     * @param commentId
     * @return
     */
    RecordBean<String> accept(Integer commentId);



    /**
     * 获取用户的评论列表
     * @param uid
     * @return
     */
    List<CommentList> getCommentList(Integer uid);
}
