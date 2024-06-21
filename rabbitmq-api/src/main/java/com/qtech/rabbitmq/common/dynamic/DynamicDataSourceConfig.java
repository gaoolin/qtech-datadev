package com.qtech.rabbitmq.common.dynamic;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/08 10:25:11
 * desc   :
 */


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 多数据源配置类
 */
@Configuration
public class DynamicDataSourceConfig {

    @Value("${spring.datasource.druid.doris-k8s.url}")
    private String url;
    @Value("${spring.datasource.druid.doris-k8s.username}")
    private String username;
    @Value("${spring.datasource.druid.doris-k8s.password}")
    private String password;

    /**
     * 实例化数据源master
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.doris-k8s")
    public DataSource dorisK8sDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 实例化数据源slave
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.postgresql")
    public DataSource postgresqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 实例化DynamicDataSource
     *
     * @param dorisK8sDataSource  masterDataSource
     * @param postgresqlDataSource slaveDataSource
     * @return DynamicDataSource
     */
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSource dorisK8sDataSource, DataSource postgresqlDataSource) {
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.DORIS_K8S, dorisK8sDataSource);
        targetDataSources.put(DataSourceNames.POSTGRESQL, postgresqlDataSource);

        return new DynamicDataSource(targetDataSources, dorisK8sDataSource);
    }
}
