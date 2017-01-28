package com.evanshare.service;

import java.util.Date;
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
public class UserService {
    @Inject
    private Dao dao;

    /**
     * 获取用户列表
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @return
     */
    public QueryResult getUserList(int pageNumber,int pageSize,String keyword){
        if(StringUtils.isEmpty(keyword)){
            keyword = "";
        }
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<User> userList =dao.query(User.class, Cnd.where("name","like","%"+keyword+"%"), pager);
        return new QueryResult(userList,pager);
    }

    /**
     * 根据姓名和密码查找用户
     * @param name
     * @param password
     * @return
     */
    public User findByNameAndPsw(String name,String password){
        return dao.fetch(User.class, Cnd.where("name", "=", name).and("password", "=", password));
    }

    public void print(){
        System.out.println("I AM NUTZ SERVICE");
    }

    /**
     * 获取用户总数
     * @return
     */
    public Integer getTotalUserCount(){

        return dao.count(User.class);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    public User addUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return dao.insert(user);

    }

     
}
