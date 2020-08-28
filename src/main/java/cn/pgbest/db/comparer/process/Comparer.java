package cn.pgbest.db.comparer.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pgbest.db.comparer.model.Table;
import cn.pgbest.db.comparer.service.OracleFactory;
import cn.pgbest.db.comparer.service.PostgresqlFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by  songzip on 2020/8/9.
 */
@Component
public class Comparer {


    @Autowired
    private PostgresqlFactory pgFactory;
    @Autowired
    private OracleFactory oracleFactory;
    
    
    public void doTable() {

        //Oracle 
        Map<String, Table> oracleTableMap =  oracleFactory.getTables();
//        for (String key : oracleTableMap.keySet()) {
//            System.out.println(key + "   -> ");
//            System.out.println(oracleTableMap.get(key).toReport() );
//        }

//        Postgresql 
        Map<String, Table> pgTableMap =  pgFactory.getTables();
//        for (String key : pgTableMap.keySet()) {
//            System.out.println(key + "   -> ");
//            System.out.println(pgTableMap.get(key).toReport() );
//        }
        
        //compare
        List<String> errors = new ArrayList<>();
        for (String key : oracleTableMap.keySet()) {
            Table tOra = oracleTableMap.get(key);
            
            Table tPg = pgTableMap.get(key);
            //not exists
            if(tPg == null){
                errors.add("Not Exists in PG: " + key);
                continue;
            }
            
            if(tOra.getColumnList().size() != tPg.getColumnList().size()){

               StringBuffer sb = new StringBuffer("Not Equal : " + key + " => " + (tOra.getColumnList().size() + " :" +tPg.getColumnList().size()));
               sb.append(System.lineSeparator())
                       .append(tOra.toReport())
                       .append(System.lineSeparator())
                       .append(tPg.toReport());
                errors.add(sb.toString());
                continue;
            }
            
        }
        System.out.println("Table:");

        System.out.println("Table Only in Oracle :");
        Set tablesInOracle = new HashSet(oracleTableMap.keySet());
        tablesInOracle.removeAll(pgTableMap.keySet());

        System.out.println(Arrays.toString(tablesInOracle.toArray()));

        System.out.println("Table Only in PG :");
        Set tablesInPG = new HashSet(pgTableMap.keySet());
        tablesInPG.removeAll(oracleTableMap.keySet());

        System.out.println(Arrays.toString(tablesInPG.toArray()));


        for(String err : errors){
            System.out.println(err);
        }
        System.out.println("Finish.");
    }


    public void doView() {

        //Oracle 
        Map<String, Table> oracleTableMap =  oracleFactory.getViews();
//        for (String key : oracleTableMap.keySet()) {
//            System.out.println(key + "   -> ");
//            System.out.println(oracleTableMap.get(key).toReport() );
//        }

//        Postgresql 
        Map<String, Table> pgTableMap =  pgFactory.getViews();
//        for (String key : pgTableMap.keySet()) {
//            System.out.println(key + "   -> ");
//            System.out.println(pgTableMap.get(key).toReport() );
//        }

        //compare
        List<String> errors = new ArrayList<>();
        for (String key : oracleTableMap.keySet()) {
            Table tOra = oracleTableMap.get(key);

            Table tPg = pgTableMap.get(key);
            //not exists
            if(tPg == null){
                errors.add("Not Exists in PG: " + key);
                continue;
            }

            if(tOra.getColumnList().size() != tPg.getColumnList().size()){

                StringBuffer sb = new StringBuffer("Not Equal : " + key + " => " + (tOra.getColumnList().size() + " :" +tPg.getColumnList().size()));
                sb.append(System.lineSeparator())
                        .append(tOra.toReport())
                        .append(System.lineSeparator())
                        .append(tPg.toReport());
                errors.add(sb.toString());
                continue;
            }

        }
        System.out.println("Table:");

        System.out.println("Table Only in Oracle :");
        Set tablesInOracle = new HashSet(oracleTableMap.keySet());
        tablesInOracle.removeAll(pgTableMap.keySet());

        System.out.println(Arrays.toString(tablesInOracle.toArray()));

        System.out.println("Table Only in PG :");
        Set tablesInPG = new HashSet(pgTableMap.keySet());
        tablesInPG.removeAll(oracleTableMap.keySet());

        System.out.println(Arrays.toString(tablesInPG.toArray()));


        for(String err : errors){
            System.out.println(err);
        }
        System.out.println("Finish.");
    }
}
