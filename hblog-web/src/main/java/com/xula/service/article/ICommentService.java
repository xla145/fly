package com.xula.service.article;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.extend.CommentList;

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

}
