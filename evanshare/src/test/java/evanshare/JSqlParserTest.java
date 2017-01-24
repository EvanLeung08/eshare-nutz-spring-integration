package evanshare;

import java.util.List;


import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.Test;

public class JSqlParserTest {

    
    @Test
    public void test() throws JSQLParserException{
        String insql ="select top 100 * from exam "
+"where s_date<"
+"("
+"select min(T.s_date) from ( select top 99 s_date from exam order by s_date desc ) as T"
+")"
+"order by s_date desc";
        Statement statement = CCJSqlParserUtil.parse(insql);
        Select selectStatement = (Select)statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> result = tablesNamesFinder.getTableList(selectStatement);
        for(String sql : result){
            System.out.println(sql);
        }
    }
    
    
}
