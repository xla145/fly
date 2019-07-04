package com.xula.dao.one;

import com.xula.entity.BookmarkList;
import com.xula.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 书签管理
 * @author xla
 */
@Repository("IBookMarkMapper")
public interface IBookMarkMapper {

    List<BookmarkList> getBookmarkList();

}