package com.xula.config.mybatis;

import com.xula.config.datasource.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = OneDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "oneSqlSessionFactory")
@ConfigurationProperties("mybatis")
public class OneDataSourceConfig {

    /**
     * 精确到 master 目录，以便跟其他数据源隔离
     */
    static final String PACKAGE = "com.xula.dao.one";
    static final String MAPPER_LOCATION = "classpath:mapper/one/*.xml";

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;


    /**
     * 事务
     * @param dataSource
     * @return
     */
    @Bean(name = "oneTransactionManager")
    @Primary
    public DataSourceTransactionManager oneTransactionManager(@Qualifier("dataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 操作类工厂
     * @param masterDataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "oneSqlSessionFactory")
    @Primary
    public SqlSessionFactory oneSqlSessionFactory(@Qualifier("dataSourceOne") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(OneDataSourceConfig.MAPPER_LOCATION));

        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        return sessionFactory.getObject();
    }
}