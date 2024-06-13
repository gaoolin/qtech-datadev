package com.qtech.olp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/13 11:17:19
 * desc   :
 */

@Configuration
@MapperScan("com.qtech.olp.mapper")
public class MyBatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // 可以在这里添加更多配置
        // 设置Mapper XML文件的位置
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/**/*.xml")); // 根据实际情况调整路径

        // 如果有配置文件，也可以在这里设置
//        sessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));

        // 硬编码类型别名的包
        sessionFactory.setTypeAliasesPackage("com.qtech.olp.entity"); // 替换为你的实际包路径

        return sessionFactory.getObject();
    }
}