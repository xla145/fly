package com.xula.config.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xula.config.datasource.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 扫描 Mapper 接口并容器管理
 * @author xla
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@Import(DataSourceConfig.class)
@MapperScan(basePackages = TwoDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "twoSqlSessionFactory")
public class TwoDataSourceConfig {

    /**
     * 精确到 cluster 目录，以便跟其他数据源隔离
     */
    static final String PACKAGE = "com.xula.dao.two";
    static final String MAPPER_LOCATION = "classpath:mapper/two/*.xml";


    /**
     * 事务
     * @param dataSourceTwo
     * @return
     */
    @Bean(name = "twoTransactionManager")
    public DataSourceTransactionManager twoTransactionManager(@Qualifier("dataSourceTwo") DataSource dataSourceTwo) {
        return new DataSourceTransactionManager(dataSourceTwo);
    }


    /**
     * 操作类工厂
     * @param dataSourceTwo
     * @return
     * @throws Exception
     */
    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSourceTwo)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceTwo);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(TwoDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}