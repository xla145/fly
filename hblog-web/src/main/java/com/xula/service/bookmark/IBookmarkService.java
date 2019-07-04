package com.xula.service.bookmark;


import com.xula.entity.BookmarkCategory;
import com.xula.entity.BookmarkList;

import java.util.List;

/**
 * 获取书签服务层
 * @author xla
 */
public interface IBookmarkService {


    List<BookmarkCategory> getBookmarkCategoryList();


    List<BookmarkList> getBookmarkList();

}
