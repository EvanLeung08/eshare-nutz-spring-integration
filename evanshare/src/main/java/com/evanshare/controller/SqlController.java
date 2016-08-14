package com.evanshare.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.JspView;

import com.alibaba.druid.pool.DruidDataSource;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

@IocBean
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
public class SqlController extends BaseModule{
    
    @At("/sql/sql_query")
    public View forwardToSqlQuery(){
        
        return new JspView("jsp/sql/sql_query");
    }
    
    @At("/sql/exportData")
    public void exportSqlData(@Param("querySql") String querySql,HttpServletResponse resp) throws FileNotFoundException, SQLException, IOException, JSQLParserException{
        exportExcelBySql(querySql,resp);
    }
    
    /**
     * @param conn
     * @throws SQLException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws JSQLParserException
     */
    private  void exportExcelBySql(String insql,HttpServletResponse resp)
            throws SQLException, IOException, FileNotFoundException, JSQLParserException {
        // 11:创建一个excel
        SXSSFWorkbook book = new SXSSFWorkbook(1000);
        //HSSFWorkbook book = new HSSFWorkbook();
        java.sql.ResultSet rs = null;
       List<String> tables =  getTables(insql);
       DataSource datasource =  Mvcs.getIoc().get(DruidDataSource.class, "dataSource");
       Connection conn = datasource.getConnection();
        // 3:声明st
        Statement st = (Statement) conn.createStatement();
        
       //结果集总行数
        double totalcount = 137713;
        
        //excel单表最大行数是65535
        double maxRowNum = 60000;
        
        int sheets = (int) Math.ceil(totalcount/maxRowNum);
        rs = st.executeQuery(insql);
        // 3:rsmd
        ResultSetMetaData rsmd = (ResultSetMetaData)rs.getMetaData();
        // 4:分析一共多少列
        int cols = rsmd.getColumnCount();
        long startTime = System.currentTimeMillis();
        
        //写入excel文件数据信息
        for(int i=0;i<sheets;i++){
            System.err.println("-------正在导出---- ");

            // 12:创建sheet
            SXSSFSheet sheet = book.createSheet(" "+(i+1)+" ");
           
            // 13:创建表头
            SXSSFRow row = sheet.createRow(0);

            for (int j = 0; j < cols; j++) {
                String cname = rsmd.getColumnName(j + 1);

                SXSSFCell cell = row.createCell(j);
                cell.setCellValue(cname);

            }
            // 显示数据
           for(int t=0;t<maxRowNum;t++){
               if(!rs.next()){
                   break;
               }
               // 14：保存数据
               row = sheet.createRow(sheet.getLastRowNum() + 1);

               for (int j = 0; j < cols; j++) {
                   SXSSFCell cell = row.createCell(j);
                   cell.setCellValue(rs.getString(j + 1));
               }
           }
           
           
         /*   while (rs.next()) {
                // 14：保存数据
                row = sheet.createRow(sheet.getLastRowNum() + 1);

                for (int j = 0; j < cols; j++) {
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue(rs.getString(j + 1));

                }
            }*/
       }
        
        resp.reset();
        resp.setContentType("multipart/form-data"); //自动识别
        resp.setHeader("Content-Disposition","attachment;filename=data.xls");
        book.write(resp.getOutputStream());
        book.close();
        long endTime = System.currentTimeMillis();
        System.out.println("导出报表所用时间为:"+(endTime-startTime));
    }
    public  List<String> getTables(String insql) throws JSQLParserException{
        net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(insql);
        Select selectStatement = (Select)statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> result = tablesNamesFinder.getTableList(selectStatement);
        return result;
    }

}
