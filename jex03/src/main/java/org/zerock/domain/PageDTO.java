package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria criteria;

    public PageDTO(Criteria criteria, int total) {
        this.total = total;
        this.criteria = criteria;

        this.endPage = (int) Math.ceil(criteria.getPageNum() / 10.0) * 10;
        this.startPage = endPage - 9;

        int realEnd = (int) Math.ceil((total * 1.0) / criteria.getAmount());

        if(realEnd < endPage)
            endPage = realEnd;

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }
}
