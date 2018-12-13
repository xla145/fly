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
import java.util.HashMap;
import java.util.Map;

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
    public DataSourceHolder dataSourceHolder(@Qualifier("dataSourceOne") DataSource dataSource,@Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
        DataSourceHolder dataSourceHolder = new DataSourceHolder();
        dataSourceHolder.setDefaultTargetDataSource(dataSource);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put("base",dataSource);
        targetDataSources.put("two",dataSourceTwo);
        dataSourceHolder.setTargetDataSources(targetDataSources);
        return dataSourceHolder;
    }


    /**
     * 创建事务
     */
    @Bean("baseTxManager")
    public DataSourceTransactionManager baseTxManager(@Qualifier("dataSourceOne") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }



    /**
     * 创建事务
     */
    @Bean("twoTxManager")
    public DataSourceTransactionManager twoTxManager(@Qualifier("dataSourceTwo") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
