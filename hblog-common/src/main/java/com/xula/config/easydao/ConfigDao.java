package com.xula.config.easydao;

import cn.assist.easydao.dao.datasource.DataSourceHolder;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xula.config.datasource.DataSourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 启用easy-dao框架
 * @author xla
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@Import(DataSourceConfig.class)
public class ConfigDao {

    /**
     * 使用 easy-dao
     * @return
     */
    @Bean
    public DataSourceHolder dataSourceHolder(@Qualifier("dataSourceOne") DataSource dataSource) {
        DataSourceHolder dataSourceHolder = new DataSourceHolder();
        dataSourceHolder.setDataSource(dataSource);
        DataSourceHolder.setDev(true);
        return dataSourceHolder;
    }


    /**
     * 创建事务
     */
    @Bean
    public DataSourceTransactionManager txManager(@Qualifier("dataSourceOne") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
