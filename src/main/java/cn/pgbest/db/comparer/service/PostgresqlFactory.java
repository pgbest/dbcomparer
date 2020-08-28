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
public class PostgresqlFactory implements IFactory {

    @Autowired
    @Qualifier("jdbcTemplatePG")
    private JdbcTemplate jdbcTemplate;


    @Value("${compare.pg.schema}")
    private String schemaName;

    @Override
    public Map<String, Table> getTables() {


        return getTableOrView("BASE TABLE");
        
//        String sql =
//                "SELECT table_name,  " +
//                        "       column_name, data_type, " +
//                        "       character_maximum_length  " +
//                        "  FROM information_schema.columns " +
//                        " WHERE table_schema = '" + this.schemaName + "' order by 1, 2";
//
//        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
//
//        Map<String, Table> tableMap = new HashMap<>();
//        // one row one column
//        for (Map<String, Object> row : results) {
//            String tableName = row.get("table_name").toString().toUpperCase();
//
//            Table table = tableMap.get(tableName);
//            if (table == null) {
//                table = new Table();
//                table.setName(tableName);
//                tableMap.put(tableName, table);
//            }
//
//            //Column
//            Column column = new Column();
//            column.setName(row.get("column_name").toString());
//            column.setType(row.get("data_type").toString());
//            column.setLength(row.get("character_maximum_length") != null ? Integer.parseInt(row.get("character_maximum_length").toString()) : 0);
//
//            table.getColumnList().add(column);
//
//        }
//
//        return tableMap;

    }

    @Override
    public Map<String, Table> getViews() {

        return getTableOrView("VIEW");
    }

    private Map<String, Table> getTableOrView( String tableType) {
        String sql =
                "select  t.table_schema as table_schema,   t.table_name as view_name,\n" +
                        "       c.column_name,\n" +
                        "       c.data_type,\n" +
                        "      c.character_maximum_length    \n" +
                        "       from information_schema.tables t\n" +
                        "    left join information_schema.columns c \n" +
                        "              on t.table_schema = c.table_schema \n" +
                        "              and t.table_name = c.table_name " +
                        " WHERE table_type = '" + tableType + "' and t.table_schema not in ('information_schema', 'pg_catalog') " +
                        " order by 1, 2, 3  ";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        Map<String, Table> tableMap = new HashMap<>();
        // one row one column
        for (Map<String, Object> row : results) {

            if (!"ppmc".equalsIgnoreCase(row.get("table_schema").toString())) {
                continue;
            }
            String tableName = row.get("view_name").toString().toUpperCase();

            Table table = tableMap.get(tableName);
            if (table == null) {
                table = new Table();
                table.setName(tableName);
                tableMap.put(tableName, table);
            }

            //Column
            Column column = new Column();
            column.setName(row.get("column_name").toString());
            column.setType(row.get("data_type").toString());
            column.setLength(row.get("character_maximum_length") != null ? Integer.parseInt(row.get("character_maximum_length").toString()) : 0);

            table.getColumnList().add(column);

        }

        return tableMap;
    }
}
