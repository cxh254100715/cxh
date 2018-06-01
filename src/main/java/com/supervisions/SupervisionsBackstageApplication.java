package com.supervisions;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.supervisions.modules.*.dao")
@Configuration
public class SupervisionsBackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupervisionsBackstageApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  backstage启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
