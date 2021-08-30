package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.config.RootConfig;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class SampleTxServiceTest {

    @Setter(onMethod_ = @Autowired)
    private SampleTxService service;

    @Test
    public void testLong() {
        String str= "Long String with random text." +
                "Long String with random text." +
                "Long String with random text." +
                "Long String with random text." +
                "Long String with random text.";
        log.info(str.getBytes(StandardCharsets.UTF_8).length);

        service.addData(str);
    }
}