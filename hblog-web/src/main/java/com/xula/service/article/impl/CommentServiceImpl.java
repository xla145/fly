package com.xula.service.article.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.Sort;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.common.SqlSort;
import cn.assist.easydao.dao.BaseDao;
import cn.assist.easydao.pojo.PagePojo;
import cn.assist.easydao.pojo.RecordPojo;
import com.xula.base.constant.ArticleConstant;
import com.xula.base.utils.ImgUtil;
import com.xula.base.utils.RecordBean;
import com.xula.entity.ArticleComment;
import com.xula.entity.ArticleCommentLove;
import com.xula.entity.extend.CommentList;
import com.xula.service.article.BaseService;
import com.xula.service.article.ICommentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 评论实现层
 * @author xla
 */
@Service("ICommentService")
public class CommentServiceImpl extends BaseService implements ICommentService {


    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * 评论
     * @param aid
     * @param content
     * @return
     */
    @Override
    public RecordBean<String> reply(String aid, String content) {
        content = ImgUtil.replaceContent(content);
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(aid);
        articleComment.setContent(content);
        articleComment.setCreateTime(new Date());
        articleComment.setUid(getUid());
        int result = BaseDao.dao.insert(articleComment);
        if (result == 0) {
            return RecordBean.error("评论失败！");
        }
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE article SET comment_num = comment_num + 1 WHERE aid = ?");
        result = BaseDao.dao.update(sql.toString(),aid);
        if (result == 0) {
            return RecordBean.error("更新文章评论数失败！");
        }
        return RecordBean.success("评论失败！");
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
        page.setPageData(dealCommentList(page.getPageData()));
        return page;
    }


    /**
     * 处理评论列表
     */

    List<CommentList> dealCommentList(List<CommentList> commentLists) {
        if (CollectionUtils.isEmpty(commentLists)) {
            return new ArrayList<>();
        }
        List<Integer> commentIds = commentLists.stream().map(CommentList::getId).collect(Collectors.toList());
        List<ArticleCommentLove> list = BaseDao.dao.queryForListEntity(ArticleCommentLove.class,new Conditions("ac_id", SqlExpr.IN,commentIds.toArray()));
        Map<Integer,ArticleCommentLove> map = list.stream().collect(Collectors.toMap(ArticleCommentLove::getAcId,a -> a));
        for (CommentList commentList:commentLists) {
            ArticleCommentLove articleCommentLove = map.get(commentList.getId());
            if (articleCommentLove != null && getUid().equals(articleCommentLove.getUid()) && articleCommentLove.getFever() > 0) {
                commentList.setLove(true);
            }
        }
        return commentLists;
    }

    /**
     * 评论点赞
     * @param commentId
     * @return
     */
    @Override
    @Transactional
    public RecordBean<String> love(Integer commentId,Integer fever) {
        /**
         * 向点赞love表添加一条数据，表示用户同一个评论只能点赞一次
         */
        ArticleCommentLove articleCommentLove = new ArticleCommentLove();
        articleCommentLove.setAcId(commentId);
        articleCommentLove.setUid(getUid());
        articleCommentLove.setCreateTime(new Date());
        articleCommentLove.setFever(fever);
        try {
            int result = BaseDao.dao.merge(articleCommentLove,"fever");
            if (result == 0) {
                throw new Exception("添加love表失败！");
            }
            /**
             * 更新评论点赞数
             */
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE article_comment SET love_num = (SELECT SUM(fever) FROM article_comment_love WHERE ac_id = ? GROUP BY ac_id ) WHERE id = ?");
            result = BaseDao.dao.update(sql.toString(),commentId,commentId);
            if (result == 0) {
                throw new Exception("更新点赞数失败！");
            }
        } catch (Exception e) {
            logger.error("更新点赞数失败！reason：{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("更新点赞数失败");
        }
        return RecordBean.success("成功！");
    }


    /**
     * 评论采纳
     * @param commentId
     * @return
     */
    @Override
    @Transactional
    public RecordBean<String> accept(Integer commentId) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE article_comment SET is_accept = 1 WHERE id = ?");
        try {
            int result = BaseDao.dao.update(sql.toString(),commentId);
            if (result == 0) {
                return RecordBean.error("评论采纳失败！");
            }
            // 只有审核通过的文章才能更新为已结状态
            String sql_1 = "UPDATE article SET status = ?,update_time = now() WHERE aid = (SELECT article_id FROM article_comment WHERE id = ?) AND status = ?";
            result = BaseDao.dao.update(sql_1, ArticleConstant.STATUS_SOLVED,commentId,ArticleConstant.STATUS_AUDITED);
            if (result == 0) {
                return RecordBean.error("更新文章状态失败！");
            }
        } catch (Exception e) {
            logger.error("更新点赞数失败！reason：{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RecordBean.error("更新点赞数失败");
        }
        return RecordBean.success("评论采纳成功！");
    }
}
