package cn.pgbest.db.comparer.service;

import java.util.List;
import java.util.Map;

import cn.pgbest.db.comparer.model.Table;
import cn.pgbest.db.comparer.model.View;
import org.springframework.stereotype.Component;

/**
 * Created by  songzip on 2020/8/9.
 */
@Component
public interface IFactory {
    
    Map<String, Table> getTables();
    Map<String, Table>  getViews();
}
