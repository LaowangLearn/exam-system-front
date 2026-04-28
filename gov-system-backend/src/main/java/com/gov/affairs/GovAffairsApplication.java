package com.gov.affairs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gov.affairs.mapper")
public class GovAffairsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovAffairsApplication.class, args);
    }
}
