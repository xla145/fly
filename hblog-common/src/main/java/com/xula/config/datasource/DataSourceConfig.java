package com.xula.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 配置数据源
 * @author xla
 */
@AutoConfigureBefore
public class DataSourceConfig {

    /**
     * 获取数据源1
     * @return
     */
    @Bean("dataSourceOne")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 获取数据源2
     * @return
     */
    @Bean("dataSourceTwo")
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
