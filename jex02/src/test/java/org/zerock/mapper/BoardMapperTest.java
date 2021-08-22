package org.zerock.mapper;

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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class BoardMapperTest {
    @Setter(onMethod_ = @Autowired)
    private BoardMapper mapper;

    @Test
    public void testGetList(){
        mapper.getList().forEach(log::info);
    }

    @Test
    public void testGetTotal() {
        Criteria cri = new Criteria();
        log.info(mapper.getTotalCount(cri));
    }

    @Test
    public void testInsert() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("Newbie");

        mapper.insert(board);

        log.info(board);
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("Newbie");

        mapper.insertSelectKey(board);

        log.info(board);
    }

    @Test
    public void testReadOne(){
        log.info(mapper.read(1L));
    }

    @Test
    public void testDeleteOne(){
        log.info("Deleted : " + mapper.delete(1L));
    }

    @Test
    public void testUpdateOne(){
        BoardVO boardVO = new BoardVO();
        boardVO.setBno(4L);
        boardVO.setContent("바뀐 내용");
        boardVO.setWriter("바뀐 작가");
        boardVO.setTitle("바뀐 책");

        log.info("Changed : " + mapper.update(boardVO));
        mapper.getList();
    }

    @Test
    public void testPaging(){
        Criteria criteria = new Criteria();
        List<BoardVO> boards = mapper.getListWithPaging(criteria);

        boards.forEach(log::info);
    }

    @Test
    public void testSearch(){
        Criteria cri = new Criteria();
        cri.setKeyword("새로");
        cri.setType("TC");
        List<BoardVO> list = mapper.getListWithPaging(cri);

        list.forEach(log::info);
    }
}