package org.zerock.persistence;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.config.RootConfig;
import org.zerock.mapper.TimeMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class TimeMapperTest {
    @Setter(onMethod_ = @Autowired)
    private TimeMapper timeMapper;

    @Test
    public void testGetTime(){
        log.info(timeMapper.getClass().getName());
        log.info(timeMapper.getTime());
    }

    @Test
    public void testGetTime2() {
        log.info(timeMapper.getClass().getName());
        log.info(timeMapper.getTime2());
    }
}
