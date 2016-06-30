package com.evanshare.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import com.evanshare.entity.SysUser;

/**
 * 用户工厂类
 * Created by liangyh on 15-4-24.
 */
@IocBean
public class CustomUserEntityManager extends UserEntityManager {
    Dao dao= Mvcs.getIoc().get(Dao.class);
    private final Log log = Logs.get();

    @Override
    public org.activiti.engine.identity.User findUserById(String userId) {

        log.info("findUserById:::::::::::::::::::::::::::::::"+userId);
        UserEntity userEntity = new UserEntity();
        SysUser sysUser = dao.fetch(SysUser.class, Cnd.where("uid", "=", userId));
        userEntity.setId(userId);
        userEntity.setFirstName(sysUser.getRealName());
        userEntity.setEmail(sysUser.getEmail());
        userEntity.setRevision(1);
        return userEntity;
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        // TODO Auto-generated method stub
        log.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        Sql sql = Sqls.create("SELECT a.* FROM sys_role a,sys_user_role b WHERE a.id=b.roleid AND b.userid=@c");
        sql.params().set("c", userId);
        sql.setCallback(Sqls.callback.maps());
        dao.execute(sql);
        List<Map> list=sql.getList(Map.class);
        List<Group> groupList=new ArrayList<Group>();
        for (Map m:list){
            GroupEntity group=new GroupEntity();
            group.setId(Strings.sNull(m.get("id")));
            group.setName(Strings.sNull(m.get("name")));
            group.setType("assignment");
            group.setRevision(1);
            groupList.add(group);
        }
        return groupList;
    }


}