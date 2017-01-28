package com.evanshare.spring.service;

import org.nutz.dao.Dao;
import org.springframework.transaction.annotation.Transactional;

/**
 * 该类用于事务测试用途
 * Created by liangyh on 2017/1/28.
 * Email:10856214@163.com
 */
public class NutzTransTestService {

    protected Dao dao;

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    @Transactional(rollbackFor=Exception.class)
    public void doUserClear() {
        dao.clear("tb_at_user");
        throw new RuntimeException();
    }

}
