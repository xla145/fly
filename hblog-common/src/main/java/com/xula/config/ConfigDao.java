package com.xula.config;

import cn.assist.easydao.dao.datasource.DataSourceHolder;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 启用easy-dao框架
 * @author xla
 */
@Configuration
public class ConfigDao {


    @Primary
    @Bean("dataSource")
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource getDataSource(){
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 使用 easy-dao
     * @return
     */
    @Bean
    public DataSourceHolder dataSourceHolder(DataSource dataSource) {
        DataSourceHolder dataSourceHolder = new DataSourceHolder();
        dataSourceHolder.setDataSource(dataSource);
        DataSourceHolder.setDev(true);
        return dataSourceHolder;
    }


    /**
     * 创建事务
     */
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
