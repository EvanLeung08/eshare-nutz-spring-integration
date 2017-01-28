package com.evanshare.spring.controller;

import com.evanshare.service.SqlService;
import com.evanshare.service.UserService;
import com.evanshare.spring.service.SpSqlService;
import com.evanshare.spring.service.SpUserService;
import net.sf.jsqlparser.JSQLParserException;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * 基于spring实现的Sql Controller
 * Created by liangyh on 2017/1/24.
 * Email:10856214@163.com
 */
@Controller
public class SpSqlController {
    @Autowired
   private SpSqlService spSqlService;
    //注入nutz service
    @Inject
    private SqlService sqlService;

    /**
     * 跳转到sql查询页面
     * @return
     */
    @RequestMapping(value = "/sql/sql_query")
    public String forwardToSqlQuery(){

        return "sql/sql_query";

    }

    /**
     * 根据自定义sql到处报表
     * @param querySql
     * @param resp
     * @throws Exception
     */
    @RequestMapping("/sql/exportData")
    public void exportSqlData(@RequestParam(value = "querySql",required = false) String querySql, HttpServletResponse resp) throws Exception {
        spSqlService.exportExcelBySql(querySql,resp);
        //调用Nutz service去到处报表
       // sqlService.exportExcelBySql(querySql,resp);
    }


}
