package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.config.RootConfig;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class BoardServiceTest {
    @Setter(onMethod_ = @Autowired)
    BoardService service;

    @Test
    public void testExist(){
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void testInsert(){
        BoardVO board = new BoardVO();
        board.setTitle("새로 입력하는 테스트");
        board.setContent("새로 입력하는 테스트 내용");
        board.setWriter("newbie");

        service.register(board);
        log.info("생성된 게시물 번호 : " + board.getBno());
    }

    @Test
    public void testGet() {
        log.info(service.get(5L));
    }

    @Test
    public void testGetList() {
        service.getList(new Criteria()).forEach(log::info);
    }

    @Test
    public void testDelete() {
        log.info("Remove Result : " + service.remove(2L));
    }

    @Test
    public void testUpdate() {
        BoardVO boardVO = service.get(6L);

        if (boardVO == null) return;

        boardVO.setTitle("수정 제목");
        log.info("Modify result : " + service.modify(boardVO));
    }

}