package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardMapper mapper;

    @Override
    public void register(BoardVO board) {
        log.info("register : " + board);
        mapper.insertSelectKey(board);
    }

    @Override
    public BoardVO get(Long bno) {
        BoardVO board = mapper.read(bno);
        log.info("get : " + board);
        return board;
    }

    @Override
    public boolean modify(BoardVO board) {
        log.info("modify : " + board.getBno());
        return mapper.update(board) == 1;
    }

    @Override
    public boolean remove(Long bno) {
        log.info("remove : " + bno);
        return mapper.delete(bno) == 1;
    }

    @Override
    public List<BoardVO> getList(Criteria criteria) {

        return mapper.getListWithPaging(criteria);
    }
}
