package com.xula.controller.bookmark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.controller.WebController;
import com.xula.entity.BookmarkCategory;
import com.xula.entity.BookmarkList;
import com.xula.service.bookmark.IBookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 书签管理
 * @author xla
 */
@RequestMapping("/bookmark")
@Controller
public class BookMarkController extends WebController{


    @Autowired
    private IBookmarkService iBookmarkService;

    /**
     * 书签首页
     * @return
     */
    @GetMapping(value = {"","index"})
    public String index(Model model) {
        List<BookmarkCategory> bookmarkCategories =  iBookmarkService.getBookmarkCategoryList();
        List<BookmarkList> bookmarkLists =  iBookmarkService.getBookmarkList();

        System.out.println(JSON.toJSON(bookmarkLists));

        // 获取书签分类
//        model.addAttribute("catList",bookmarkCategories);
        model.addAttribute("bookmarkList",bookmarkLists);
        // 获取分类列表
        return "/bookmark/index";
    }


    /**
     * 书签
     * @return
     */
    @PostMapping(value = "/list")
    public JSONObject list() {
        return null;
    }


}
