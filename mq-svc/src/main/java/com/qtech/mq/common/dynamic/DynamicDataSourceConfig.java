package com.qtech.mq.common.dynamic;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/08 10:25:11
 * desc   :
 */


/**
 * 多数据源配置类
 */
@Configuration
public class DynamicDataSourceConfig {

    @Value("${spring.datasource.druid.first.url}")
    private String firstUrl;
    @Value("${spring.datasource.druid.first.username}")
    private String firstUsername;
    @Value("${spring.datasource.druid.first.password}")
    private String firstPassword;

    @Value("${spring.datasource.druid.second.url}")
    private String secondUrl;
    @Value("${spring.datasource.druid.second.username}")
    private String secondUsername;
    @Value("${spring.datasource.druid.second.password}")
    private String secondPassword;


    /**
     * 实例化数据源master
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 实例化数据源slave
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 实例化DynamicDataSource
     *
     * @param firstDataSource  masterDataSource
     * @param secondDataSource slaveDataSource
     * @return DynamicDataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSource firstDataSource, DataSource secondDataSource) {
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);

        return new DynamicDataSource(targetDataSources, firstDataSource);
    }

    /**
     * 固定使用第一个数据源配置jdbcTemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    @Qualifier("firstJdbcTemplate")
    public JdbcTemplate firstJdbcTemplate(@Qualifier("firstDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * 固定使用第二个数据源配置jdbcTemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    @Qualifier("secondJdbcTemplate")
    public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
