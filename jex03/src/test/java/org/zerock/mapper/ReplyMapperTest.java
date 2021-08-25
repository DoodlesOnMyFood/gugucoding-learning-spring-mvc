package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.config.RootConfig;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Log4j
@ContextConfiguration(classes = {RootConfig.class})
public class ReplyMapperTest {
    @Setter(onMethod_ = {@Autowired})
    private ReplyMapper mapper;
    private Long[] bnoArr = {2492599L, 2492600L, 2492601L,2492602L, 2492603L};
    @Test
    public void testMapper() {
        log.info(mapper);
    }

    @Test
    public void testCreate() {
        IntStream.range(1, 10).forEach(i -> {
            ReplyVO vo = new ReplyVO();
            vo.setBno(bnoArr[i % 5]);
            vo.setReply("댓글 테스트 : " + i);
            vo.setReplyer("replyer " + i);
            mapper.insert(vo);
        });
    }

    @Test
    public void testRead() {
        Long targetRno = 5L;
        ReplyVO vo = mapper.read(targetRno);
        log.info(vo);
    }

    @Test
    public void testDelete() {
        Long targetRno = 1L;
        log.info(mapper.delete(targetRno));
    }

    @Test
    public void testUpdate(){
        ReplyVO vo = new ReplyVO();
        vo.setReply("업데이트 테스트");
        vo.setRno(5L);
        mapper.update(vo);
        log.info(mapper.read(5L));
    }

    @Test
    public void testList() {
        Criteria cri = new Criteria();
        List<ReplyVO> list = mapper.getListWithPaging(cri, 2492600L);
        list.forEach(log::info);
    }

    @Test
    public void testList2() {
        Criteria criteria = new Criteria(2, 10);
        mapper.getListWithPaging(criteria, 4718692L).forEach(log::info);
    }

    @Test
    public void testCount() {
        log.info(mapper.getCountByBno(4718692L));
    }
}