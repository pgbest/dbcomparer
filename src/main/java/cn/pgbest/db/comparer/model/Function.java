package cn.pgbest.db.comparer.model;

import java.util.Set;

import lombok.Data;

/**
 * Created by  songzip on 2020/8/9.
 */
@Data
public class Function {
    String name;
    Set<String> parameters;
    String ret;
}
