package org.zerock.persistence;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.config.RootConfig;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class DataSourceTest {

    @Setter(onMethod_ = @Autowired)
    private DataSource dataSource;

    @Setter(onMethod_ = @Autowired)
    private SqlSessionFactory sessionFactory;

    @Test
    public void testConnection() {
        try(Connection con = dataSource.getConnection()){
            log.info(con);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testMyBatis() {
        try(SqlSession session = sessionFactory.openSession();
            Connection connection = session.getConnection();
        ){
            log.info(session);
            log.info(connection);
        } catch (Exception e){
            fail(e.getMessage());
        }
    }
}

