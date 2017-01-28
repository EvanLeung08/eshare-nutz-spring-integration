package com.evanshare.module;

import javax.servlet.http.HttpServletResponse;

import com.evanshare.service.SqlService;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.JspView;

@IocBean
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
public class SqlModule extends BaseModule{

    @Inject
    private SqlService sqlService;

    @At("/sql/sql_query")
    public View forwardToSqlQuery(){

        return new JspView("jsp/sql/sql_query");
    }
    
    @At("/sql/exportData")
    public void exportSqlData(@Param("querySql") String querySql,HttpServletResponse resp) throws Exception{
        //根据sql自动生成报表
        sqlService.exportExcelBySql(querySql,resp);
    }

}
