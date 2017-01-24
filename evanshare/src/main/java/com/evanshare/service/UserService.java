package com.evanshare.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.alibaba.druid.util.StringUtils;
import com.evanshare.bean.User;

@IocBean
@InjectName("userService")
public class UserService {
    @Inject
    private Dao dao;
    
    public QueryResult getUserList(int pageNumber,int pageSize,String keyword){
        /*Pager pager = new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
       
        List<User> userList =  dao.query(User.class, null, pager);
        List<User> totalUserList =  dao.query(User.class, null);
        pager.setRecordCount(totalUserList.size());*/
        if(StringUtils.isEmpty(keyword)){
            keyword = "";
        }
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<User> userList =dao.query(User.class, Cnd.where("name","like","%"+keyword+"%"), pager);
        return new QueryResult(userList,pager);
    }
     
}
