package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReplyPageDto {
    private int replyCnt;
    private List<ReplyVO> list;
}
