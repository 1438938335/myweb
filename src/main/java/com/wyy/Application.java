package com.wyy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.wyy.dao")
@EnableCaching
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }
}
