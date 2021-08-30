package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDto;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import java.util.List;

@Log4j
@Service
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyMapper replyMapper;
    private final BoardMapper boardMapper;


    @Transactional
    @Override
    public int register(ReplyVO vo) {
        log.info("register......." + vo);
        boardMapper.updateReplyCnt(vo.getBno(), 1);
        return replyMapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get........." + rno);
        return replyMapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        log.info("modify............" + vo);
        return replyMapper.update(vo);
    }

    @Transactional
    @Override
    public int remove(Long rno) {
        log.info("remove............" + rno);
        ReplyVO replyVO = replyMapper.read(rno);
        boardMapper.updateReplyCnt(replyVO.getBno(), -1);
        return replyMapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria criteria, Long bno) {
        log.info("get reply list of board " + bno);
        return replyMapper.getListWithPaging(criteria, bno);
    }

    @Override
    public ReplyPageDto getListPage(Criteria criteria, Long bno) {
        return new ReplyPageDto(replyMapper.getCountByBno(bno), getList(criteria, bno));
    }
}
