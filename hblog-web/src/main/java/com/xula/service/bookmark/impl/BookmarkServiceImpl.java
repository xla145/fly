package com.xula.service.bookmark.impl;

import cn.assist.easydao.common.Conditions;
import cn.assist.easydao.common.SqlExpr;
import cn.assist.easydao.dao.BaseDao;
import com.xula.dao.one.IBookMarkMapper;
import com.xula.entity.BookmarkCategory;
import com.xula.entity.BookmarkList;
import com.xula.service.bookmark.IBookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 书签管理
 * @author xla
 */
@Service
public class BookmarkServiceImpl implements IBookmarkService {


    @Autowired
    private IBookMarkMapper bookMarkMapper;

    @Override
    public List<BookmarkCategory> getBookmarkCategoryList() {
        Conditions conn = new Conditions("status", SqlExpr.EQUAL,1);
        return BaseDao.dao.queryForListEntity(BookmarkCategory.class,conn);
    }

    @Override
    public List<BookmarkList> getBookmarkList() {
        return bookMarkMapper.getBookmarkList();
    }
}
