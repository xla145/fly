package com.xula.controller.article;


import com.alibaba.fastjson.JSONObject;
import com.xula.base.auth.Login;
import com.xula.base.utils.JsonBean;
import com.xula.base.utils.RecordBean;
import com.xula.service.article.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 评论控制层
 * @author xla
 */
@RequestMapping("/comment")
@Controller
public class CommentController {


    @Autowired
    private ICommentService iCommentService;


    /**
     * 用户评论
     * @param aid
     * @param content
     * @return
     */
    @Login
    @RequestMapping("/reply")
    @ResponseBody
    public JSONObject reply(@RequestParam(name = "aid") String aid,@RequestParam(name = "content") String content) {
        RecordBean<String> result = iCommentService.reply(aid,content);
        if (result.isSuccessCode()) {
            return JsonBean.success(result.getMsg());
        }
        return JsonBean.error(result.getMsg());
    }
}
