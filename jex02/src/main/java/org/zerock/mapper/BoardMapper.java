package org.zerock.mapper;


import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

public interface BoardMapper {
    //@Select("select * from tbl_board where bno > 0")
    public List<BoardVO> getList();

    public void insert(BoardVO vo);

    public void insertSelectKey(BoardVO vo);

    public BoardVO read(Long id);

    public int delete(Long id);

    public int update(BoardVO vo);

    public List<BoardVO> getListWithPaging(Criteria criteria);


}
