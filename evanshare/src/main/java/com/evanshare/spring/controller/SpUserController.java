package com.evanshare.spring.controller;

import com.evanshare.bean.User;
import com.evanshare.entity.PagerModel;
import com.evanshare.service.UserService;
import com.evanshare.spring.service.SpUserService;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 基于spring实现的用户controller
 * Created by liangyh on 2017/1/28.
 * Email:10856214@163.com
 */
@RestController
public class SpUserController {
 @Autowired
    private SpUserService spUserService;
    //注入nutz的service
    @Inject
    private UserService userService;

    /**
     * 用户登录
     *
     * @param name
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String name, @RequestParam("password") String password, HttpSession session) {
        User user = spUserService.findByNameAndPsw(name,password);
        if (user == null) {
            return "/page/error/500";
        } else {
            session.setAttribute("user", user);
            return "jsp/common/index_menu";
        }
    }


    /**
     * 获取用户列表
     *
     * @param session
     * @return
     */
    @RequestMapping("/user/list")
    public String list(@RequestParam(value = "pageNum",required = false) String pageNum,@RequestParam(value = "numPerPage",required = false) String numPerPage ,@RequestParam(value = "keyword",required = false) String keyword,HttpSession session,HttpServletRequest req) {
        Integer page = StringUtils.isNotBlank(pageNum)?Integer.parseInt(pageNum):0;
        Integer perPageNum = StringUtils.isNotBlank(numPerPage)?Integer.parseInt(numPerPage):0;
        //调用Nutz service获取用户列表
       // QueryResult result  = userService.getUserList(page, perPageNum,keyword);
        QueryResult result  = spUserService.getUserList(page, perPageNum,keyword);
        List<User> users =  result.getList(User.class);
        PagerModel pager = new PagerModel();
        pager.setCurrentPage(page);
        pager.setNumPerPage(perPageNum);
        pager.setPageNumShown(result.getPager().getPageSize());
        pager.setTotalCount(result.getPager().getRecordCount());
        pager.setPageSize(result.getPager().getPageSize());
        req.setAttribute("users", users);
        req.setAttribute("pager", pager);
        return "user/user_list";
    }

}
