package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * TBL_BOARD
 * @author 
 */
@Data
public class TblBoard implements Serializable {
    private Long bno;

    private String title;

    private String content;

    private Date regdate;

    private Date updatedate;

    private String writer;

    private static final long serialVersionUID = 1L;
}