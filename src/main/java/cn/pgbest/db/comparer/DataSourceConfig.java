package cn.pgbest.db.comparer;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by  songzip on 2020/8/9.
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "oracle")
    @Qualifier("oracle")
    @ConfigurationProperties(prefix="spring.datasource.oracle")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "jdbcTemplateOracle")
    public JdbcTemplate jdbcTemplate1(@Qualifier("oracle") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "postgresql")
    @Qualifier("postgresql")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.postgresql")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "jdbcTemplatePG")
    public JdbcTemplate jdbcTemplate2(@Qualifier("postgresql") DataSource ds) {
        return new JdbcTemplate(ds);
    }
    
    
    
}
