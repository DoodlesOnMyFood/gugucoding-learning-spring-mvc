package com.example.jex04.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.sql.jdbcapi.DriverSpy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.example.jex04.service"})
@ComponentScan(basePackages = {"com.example.jex04.aop"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan
public class RootConfig {

    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DriverSpy.class.getName());
        config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
        config.setPassword("book_ex");
        config.setUsername("book_ex");

        return new HikariDataSource(config);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager txManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
