package com.xula.config.easydao;

import cn.assist.easydao.dao.datasource.DataSourceHolder;
import cn.assist.easydao.interceptor.DataSourceInterceptor;
import com.xula.base.constant.DataSourceConstant;
import com.xula.config.datasource.DataSourceConfig;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Method;
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
        // 传入默认的数据源
        dataSourceHolder.setDefaultTargetDataSource(dataSource);
        // 创建map存数据源信息
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceConstant.DATA_SOURCE_A,dataSource);
        targetDataSources.put(DataSourceConstant.DATA_SOURCE_B,dataSourceTwo);
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


    /**
     * 定义一个数据源拦截器
     * @return
     */
    @Bean
    public DataSourceInterceptor getDataSourceInterceptor() {
        return new DataSourceInterceptor();
    }

    /**
     * 定义一个切面
     * @return
     */
    @Bean
    public AbstractPointcutAdvisor getAbstractPointcutAdvisor() {

        /**
         * 配置aop
         */
        AbstractPointcutAdvisor abstractPointcutAdvisor = new AbstractPointcutAdvisor() {

            /**
             * 配置aop规则
             */
            StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    return method.isAnnotationPresent(cn.assist.easydao.annotation.DataSource.class) || targetClass.isAnnotationPresent(cn.assist.easydao.annotation.DataSourceConfig.class);
                }
            };

            @Override
            public Pointcut getPointcut() {
                return pointcut;
            }

            @Override
            public Advice getAdvice() {
                return getDataSourceInterceptor();
            }
        };
        return abstractPointcutAdvisor;
    }
}
