package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDto;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import java.util.List;

@Log4j
@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        log.info("register......." + vo);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get........." + rno);
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        log.info("modify............" + vo);
        return mapper.update(vo);
    }

    @Override
    public int remove(Long rno) {
        log.info("remove............" + rno);
        return mapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria criteria, Long bno) {
        log.info("get reply list of board " + bno);
        return mapper.getListWithPaging(criteria, bno);
    }

    @Override
    public ReplyPageDto getListPage(Criteria criteria, Long bno) {
        return new ReplyPageDto(mapper.getCountByBno(bno), getList(criteria, bno));
    }
}