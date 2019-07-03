package com.xula.controller;


import cn.assist.easydao.pojo.PagePojo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xula.base.constant.GlobalConstant;
import com.xula.base.constant.ImgCategory;
import com.xula.base.constant.PageConstant;
import com.xula.base.utils.*;
import com.xula.dao.one.MemberMapper;
import com.xula.dao.two.UserMapper;
import com.xula.entity.Member;
import com.xula.entity.User;
import com.xula.entity.extend.ArticleList;
import com.xula.service.article.IArticleService;
import com.xula.service.member.IWebMemberService;
import com.xula.service.oss.IUploadFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页信息
 * @author xla
 */
@Controller
public class IndexController extends WebController{

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private IUploadFileService iUploadFileService;
    @Autowired
    private IWebMemberService iWebMemberService;


    @GetMapping(value = {"/","index"})
    public String index(Model model) {



        // 获取 filter 列表数据
        Map<String,Object> map = new HashMap<>();
        PagePojo<ArticleList> page = iArticleService.getArticlePage(null,1,GlobalConstant.PAGE_SIZE);
        map.put("type","all");
        map.put("filter","");

        model.addAttribute("page",page);
        model.addAttribute("data",map);
        return "book/index";
    }

    @GetMapping(value = "loginTest")
    @ResponseBody
    public JSONObject loginTest(HttpServletRequest request) {

        Member member = memberMapper.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(member));

        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(JSON.toJSONString(user));

        request.getSession().setAttribute(GlobalConstant.SESSION_UID,1);
        return JsonBean.success("success",1);
    }

    /**
     * 获取验证码
     * @return
     */
    @GetMapping(value = "/auth/imagecode")
    public void imageCode(HttpServletResponse response,HttpServletRequest request) {
        Captcha captcha = Captcha.getInstance();
        String vcode = captcha.getRandomString();
        /**
         * 将vcode 保存在session 中
         */
        request.getSession().setAttribute(GlobalConstant.CODE_NAME,vcode);
        Captcha.getInstance().render(response,vcode);
    }


    /**
     * 获取验证码
     * @return
     */
    @PostMapping(value = "/img/upload")
    @ResponseBody
    public JSONObject upload(@RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        try {
            String url = iUploadFileService.uploadImg(ImgUtil.inputStreamToByteArray(file.getInputStream()), ImgCategory.test);
            if (StringUtils.isEmpty(url)) {
                return JsonBean.error("上传失败！");
            }
            jsonObject.put("url",url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonBean.success("success",jsonObject);
    }


    /**
     * 发送激活链接
     * @return
     */
    @PostMapping(value = "/email/send")
    @ResponseBody
    public JSONObject activate(@RequestParam("email") String email) {
        RecordBean<String> result = iWebMemberService.sendEmail(email);
        if (!result.isSuccessCode()) {
            return JsonBean.error(result.getMsg());
        }
        return JsonBean.success("success");
    }


    /**
     * 获取验证码
     * @return
     */
    @GetMapping(value = "/email/activate")
    public String activate(HttpServletRequest request) {
        String expirationTime = WebReqUtils.getParam(request,"expirationTime",null);
        if (expirationTime == null) {
            return "redirect:"+PageConstant.PAGE_404;
        }
        Long expTime = Long.parseLong(expirationTime);
        if (expTime < System.currentTimeMillis()) {
            return "redirect:"+PageConstant.PAGE_404;
        }
        iWebMemberService.activateEmail(WebReqUtils.getSessionUid(request));
        return "redirect:"+PageConstant.INDEX;
    }

}
