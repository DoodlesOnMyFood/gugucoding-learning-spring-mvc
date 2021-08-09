package generate;

import generate.TblBoard;

public interface TblBoardDao {
    int deleteByPrimaryKey(Long bno);

    int insert(TblBoard record);

    int insertSelective(TblBoard record);

    TblBoard selectByPrimaryKey(Long bno);

    int updateByPrimaryKeySelective(TblBoard record);

    int updateByPrimaryKey(TblBoard record);
}