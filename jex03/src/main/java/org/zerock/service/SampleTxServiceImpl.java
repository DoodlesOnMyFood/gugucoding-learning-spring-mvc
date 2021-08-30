package org.zerock.service;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {

    private Sample1Mapper mapper1;
    private Sample2Mapper mapper2;

    public SampleTxServiceImpl(Sample1Mapper mapper1, Sample2Mapper mapper2) {
        this.mapper1 = mapper1;
        this.mapper2 = mapper2;
    }

    @Transactional
    @Override
    public void addData(String value) {
        log.info("Insert Mapper 1................");
        mapper1.insertCol1(value);

        log.info("Insert Mapper 2................");
        mapper2.insertCol2(value);

        log.info("End............................");
    }
}
