package cn.pgbest.db.comparer.model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Created by  songzip on 2020/8/9.
 */
@Data
@ToString
public class Table {
    String name;
    List<Column> columnList = new LinkedList<>();

    public String toReport() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name).append(" : ")
                .append(this.columnList.size())
                .append(System.lineSeparator());
        for (Column column : columnList) {
            sb.append("\t").append(column.getName()).append("\t")
                    .append(column.getType()).append("\t")
                    .append(column.getLength()).append(System.lineSeparator());
            

        }
        return sb.toString();
    }
}
