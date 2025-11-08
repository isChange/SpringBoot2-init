package com.ly.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.ly.app.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching
public class AppApplication {


    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
