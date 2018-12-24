package com.xula.service.article.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.utils.RecordBean;
import com.xula.entity.ArticleComment;
import com.xula.entity.extend.CommentList;
import com.xula.service.article.BaseService;
import com.xula.service.article.ICommentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 评论实现层
 * @author xla
 */
@Service("ICommentService")
public class CommentServiceImpl extends BaseService implements ICommentService {


    /**
     * 评论
     * @param aid
     * @param content
     * @return
     */
    @Override
    public RecordBean<String> reply(String aid, String content) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(aid);
        articleComment.setContent(content);
        articleComment.setCreateTime(new Date());
        articleComment.setUid(getUid());
        int result = BaseDao.dao.insert(articleComment);
        if (result == 1) {
            return RecordBean.success("评论成功！");
        }
        return RecordBean.error("评论失败！");
    }


    /**
     *
     * @param conn
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PagePojo<CommentList> getCommentPage(Conditions conn, Integer pageNo, Integer pageSize) {
        // 获取评论数
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.id,a.content,a.is_accept,a.uid,a.create_time,a.love_num,m.nickname,m.avatar ");
        sql.append("FROM article_comment a JOIN member m ON(a.uid = m.uid) ");
        sql.append("WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (conn != null && StringUtils.isNotEmpty(conn.getConnSql())) {
            sql.append("AND " + conn.getConnSql());
            params.addAll(conn.getConnParams());
        }
        Sort sort = new Sort("a.create_time", SqlSort.DESC);
        PagePojo<CommentList> page = BaseDao.dao.queryForListPage(CommentList.class,sql.toString(),params,sort,pageNo,pageSize);
        return page;
    }
}
