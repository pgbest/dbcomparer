package cn.pgbest.db.comparer;

import cn.pgbest.db.comparer.process.Comparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by  songzip on 2020/8/9.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private Comparer comparer;
    
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println(System.currentTimeMillis());

        comparer.doTable();
        comparer.doView();
    }


    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run(args);
        
//        SpringApplication.run(Application.class, args);
    }
    
}
