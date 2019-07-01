package com.xula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


/**
 * 启用类
 * @author xla
 */
@SpringBootApplication
@EnableCaching
public class HblogSysApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HblogSysApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HblogSysApplication.class, args);
    }
}
