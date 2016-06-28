package com.evanshare.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

/**
 * 分组工厂类
 * Created by wizzer on 15-4-27.
 */
@IocBean
public class CustomGroupEntityManager extends GroupEntityManager {
    Dao dao= Mvcs.getIoc().get(Dao.class);
    private final Log log = Logs.get();

    @Override
    public List<Group> findGroupsByUser(String userId) {
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