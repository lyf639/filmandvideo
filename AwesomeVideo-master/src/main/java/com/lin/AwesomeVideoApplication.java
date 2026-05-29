package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value={"com.lin.dao"})
@EnableCaching
public class AwesomeVideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwesomeVideoApplication.class, (String[])args);
    }
}
