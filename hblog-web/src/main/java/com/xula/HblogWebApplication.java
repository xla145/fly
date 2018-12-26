package com.xula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 网站web 启用
 * @author xla
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
public class HblogWebApplication
//        extends SpringBootServletInitializer
{

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(HblogWebApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(HblogWebApplication.class, args);
    }
}
