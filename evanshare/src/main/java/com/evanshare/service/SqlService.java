package com.evanshare.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.evanshare.module.SqlModule;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Sql处理服务类
 * Created by liangyh on 2017/1/28.
 * Email:10856214@163.com
 */
@IocBean
public class SqlService {
    private final Logger logger = Logger.getLogger(SqlModule.class);

    @Inject
    private Dao dao;

    /**
     * 根据自定义sql导出报表
     * @param insql 自定义sql
     * @param resp 响应实体
     * @throws SQLException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws JSQLParserException
     */
    public void exportExcelBySql(String insql,HttpServletResponse resp)
            throws Exception {
        // 11:创建一个excel
        SXSSFWorkbook book = new SXSSFWorkbook(1000);
        java.sql.ResultSet rs = null;
        List<String> tables =  getTables(insql);
        DataSource datasource =  Mvcs.getIoc().get(DruidDataSource.class, "dataSource");
        Connection conn = null;
        Statement st =null;
        try {
            conn = datasource.getConnection();
            // 3:声明st
            double maxRowNum;
            int sheets;
            ResultSetMetaData rsmd;
            int cols;
            long startTime;
            long endTime;
            st = conn.createStatement();

            //结果集总行数
            double totalcount = 137713;

            /* excel单表最大行数是65535 */
            maxRowNum = 60000;

            sheets = (int) Math.ceil(totalcount / maxRowNum);
            rs = st.executeQuery(insql);
            // 3:rsmd
            rsmd = (ResultSetMetaData) rs.getMetaData();
            // 4:分析一共多少列
            cols = rsmd.getColumnCount();
            startTime = System.currentTimeMillis();

            //写入excel文件数据信息
            for (int i = 0; i < sheets; i++) {
                logger.info("<=======正在导出报表=========>");

                // 12:创建sheet
                SXSSFSheet sheet = book.createSheet(" " + (i + 1) + " ");

                // 13:创建表头
                SXSSFRow row = sheet.createRow(0);

                for (int j = 0; j < cols; j++) {
                    String cname = rsmd.getColumnName(j + 1);

                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue(cname);

                }
                // 显示数据
                for (int t = 0; t < maxRowNum; t++) {
                    if (!rs.next()) {
                        break;
                    }
                    // 14：保存数据
                    row = sheet.createRow(sheet.getLastRowNum() + 1);

                    for (int j = 0; j < cols; j++) {
                        SXSSFCell cell = row.createCell(j);
                        cell.setCellValue(rs.getString(j + 1));
                    }
                }
            }

            resp.reset();
            resp.setContentType("multipart/form-data"); //自动识别
            resp.setHeader("Content-Disposition", "attachment;filename=data.xls");
            book.write(resp.getOutputStream());
            endTime = System.currentTimeMillis();
            logger.info("导出报表所用时间为:" + (endTime - startTime));
        }catch (Exception ex){
            logger.info(String.format("导出报表异常,Sql=【%s】,异常信息{}",insql),ex);

        }finally {
            if(book!=null){
                book.close();
            }
            if(rs!=null){
                rs.close();
            }
            if(st!=null){
                st.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
    }

    /**
     * 获取对应的表
     * @param insql 自定义sql
     * @return
     * @throws JSQLParserException
     */
    private  List<String> getTables(String insql) throws JSQLParserException{
        net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(insql);
        Select selectStatement = (Select)statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> result = tablesNamesFinder.getTableList(selectStatement);
        return result;
    }


}
