package com.archine;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan("com.archine.mapper")
@EnableScheduling
@EnableSwagger2
@EnableKnife4j
public class ArchineBlogApplication {
    public static void main(String[] args) {

        SpringApplication.run(ArchineBlogApplication.class,args);
    }
}
