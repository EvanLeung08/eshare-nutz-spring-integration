package com.evanshare.spring.service;

import org.junit.Test;
import org.nutz.dao.Dao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

/**
 * NUtz事务测试
 * Created by liangyh on 2017/1/28.
 * Email:10856214@163.com
 */
public class NutzTransTestServiceTest extends Assert {


    @Test
    public void doUserClear() throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        NutzTransTestService nutzTest = ctx.getBean("transTest", NutzTransTestService.class);

        Dao dao = ctx.getBean("dao", Dao.class);
        int count = dao.count("tb_at_user");
        assertTrue(count > 0);
        try {
            nutzTest.doUserClear();
        } catch (Exception e) {
            // 里面主动抛出异常
        }
        assertEquals(count, dao.count("tb_at_user"));
        ctx.close();
    }

}