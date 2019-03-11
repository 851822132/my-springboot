package com.test.myspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.test.myspringboot.dao")
@SpringBootApplication
public class MySpringbootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MySpringbootApplication.class);
    }
}
