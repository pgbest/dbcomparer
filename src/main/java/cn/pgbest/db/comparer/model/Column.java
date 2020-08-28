package cn.pgbest.db.comparer.model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by  songzip on 2020/8/9.
 */
@Data
@ToString
public class Column implements  Comparable{
    String name;
    String type;
    int length;
    String def; //default    

    @Override
    public int compareTo(Object o) {
        if(o instanceof  Column){
            Column other = (Column) o;
            return this.getName().compareTo(other.getName());
        }
        return 0;
    }
}
