package evanshare;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;


public class ExcelExportUtils {
    
    public ExcelExportUtils() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main( String[] args ) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException, JSQLParserException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/eam-mirror?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull","root","root");

        exportExcelBySql(conn);
    }

    private static void exportSpecifyExcelBySql(Connection conn)
            throws SQLException, IOException, FileNotFoundException {
        // 11:创建一个excel
        HSSFWorkbook book = new HSSFWorkbook();

        // 1：分析数据
        java.sql.DatabaseMetaData dmd = conn.getMetaData();
        // 2:获取所有表名
        java.sql.ResultSet rs = dmd.getTables("", "", "act_ge_bytearray",
                new String[] {"TABLE"});
        List<String> tables = new ArrayList<String>();

        while (rs.next()) {
            String tn = rs.getString("TABLE_NAME");
            tables.add(tn);
        }
        int table_len = tables.size();

        // 3:声明st
        Statement st = (Statement) conn.createStatement();

        int idx = 0;

        for (String tn : tables) {
            System.err.println("表:" + tn);

            idx++;
            System.err.println("-------正在导----" + idx + "/" + table_len);

            // 12:创建sheet
            HSSFSheet sheet = book.createSheet(tn);

            //String sql = "select * from " + tn;
            String sql = "select *from act_ge_property, act_ge_bytearray ";
            rs = st.executeQuery(sql);
            // 3:rsmd
            ResultSetMetaData rsmd = (ResultSetMetaData)rs.getMetaData();
            // 4:分析一共多少列
            int cols = rsmd.getColumnCount();

            // 13:创建表头
            HSSFRow row = sheet.createRow(0);

            for (int i = 0; i < cols; i++) {
                String cname = rsmd.getColumnName(i + 1);

                HSSFCell cell = row.createCell(i);
                cell.setCellValue(cname);

                // System.err.print("\t"+cname);
            }
            // System.err.println("\n");
            // 显示数据
            while (rs.next()) {
                // 14：保存数据
                row = sheet.createRow(sheet.getLastRowNum() + 1);

                for (int i = 0; i < cols; i++) {

                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(rs.getString(i + 1));

                    // System.err.print("\t"+rs.getString(i+1));
                }
                // System.err.println("\n");
            }

            // System.err.println("\n-------------------");
        }

        book.write(new FileOutputStream("d:/aa.xls"));
    }
    
    
    /**
     * @param conn
     * @throws SQLException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws JSQLParserException
     */
    private static void exportExcelBySql(Connection conn)
            throws SQLException, IOException, FileNotFoundException, JSQLParserException {
        // 11:创建一个excel
        SXSSFWorkbook book = new SXSSFWorkbook(1000);
        //HSSFWorkbook book = new HSSFWorkbook();
        java.sql.ResultSet rs = null;
        String insql = "select * from test, act_hi_procinst  ";

       List<String> tables =  getTables(insql);

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
        book.write(new FileOutputStream("d:/aa.csv"));
        book.close();
        long endTime = System.currentTimeMillis();
        System.out.println("导出报表所用时间为:"+(endTime-startTime));
    }
    
  
    
    public static List<String> getTables(String insql) throws JSQLParserException{
        net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(insql);
        Select selectStatement = (Select)statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> result = tablesNamesFinder.getTableList(selectStatement);
        return result;
    }
    
}
