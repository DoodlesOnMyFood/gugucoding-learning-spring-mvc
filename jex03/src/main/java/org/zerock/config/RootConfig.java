package org.zerock.config;

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
@MapperScan("org.zerock.mapper")
@ComponentScan(basePackages = {"org.zerock.service"})
@ComponentScan(basePackages = {"org.zerock.aop"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public DataSource dataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(DriverSpy.class.getName());
        hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
        hikariConfig.setUsername("book_ex");
        hikariConfig.setPassword("book_ex");

        return new HikariDataSource(hikariConfig);
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
