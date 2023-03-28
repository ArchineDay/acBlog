package com.archine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.archine.mapper")
@EnableScheduling
public class ArchineBlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(ArchineBlogApplication.class,args);
    }
}
