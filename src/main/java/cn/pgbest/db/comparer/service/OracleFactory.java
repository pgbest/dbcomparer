package cn.pgbest.db.comparer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pgbest.db.comparer.model.Column;
import cn.pgbest.db.comparer.model.Table;
import cn.pgbest.db.comparer.model.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by  songzip on 2020/8/9.
 */
@Component
public class OracleFactory implements IFactory {

    @Autowired
    @Qualifier("jdbcTemplateOracle")
    private JdbcTemplate jdbcTemplate;

    
    @Value("${compare.pg.schema}")
    private String schemaName;


    public Map<String, Table> getTablesOrView(String sSql) {

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sSql);

        Map<String, Table> tableMap = new HashMap<>();
        // one row one column
        for (Map<String, Object> row : results) {
            String tableName = row.get("table_name").toString();

            Table table = tableMap.get(tableName);
            if(table == null){
                table = new Table();
                table.setName(tableName);
                tableMap.put(tableName,table);
            }

            //Column
            Column column = new Column();
            column.setName(row.get("column_name").toString());
            column.setType(row.get("data_type").toString());
            column.setLength(row.get("character_maximum_length") != null ? Integer.parseInt(row.get("character_maximum_length").toString()): 0);

            table.getColumnList().add(column);

        }

        return tableMap;
        
        
    }
        
    
    @Override
    public Map<String, Table> getTables() {

        String sql = 
                "SELECT table_name,  " +
                        "       column_name, data_type, " +
                        "       data_length  " +
                        "  from USER_TAB_COLUMNS  where table_name in (select table_name from user_tables ) " +
                        "  order by 1, 2";

        return  getTablesOrView(sql);
    }

    @Override
    public  Map<String, Table>  getViews() {

        String sql =
                "SELECT table_name,  " +
                        "       column_name, data_type, " +
                        "       data_length  " +
                        "  from USER_TAB_COLUMNS  where table_name in ( select view_name  from user_views  ) " +
                        "  order by 1, 2";


        return  getTablesOrView(sql); 

    }
}
