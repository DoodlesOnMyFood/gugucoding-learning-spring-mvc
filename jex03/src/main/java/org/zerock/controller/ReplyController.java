package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDto;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import java.util.List;

@RestController
@Log4j
@AllArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService service;

    @PostMapping(value = "/new", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO vo){
        log.info("ReplyVO : " + vo);

        int insertCount = service.register(vo);

        log.info("Reply INSERT COUNT : " + insertCount);

        return insertCount == 1 ?
                new ResponseEntity<String>("success", HttpStatus.OK) :
                new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/pages/{bno}/{pages}")
    public ResponseEntity<ReplyPageDto> getList(@PathVariable Long bno, @PathVariable int pages){
        log.info("getList..........");

        Criteria criteria = new Criteria(pages,10);
        log.info(criteria);
        return new ResponseEntity<ReplyPageDto>(service.getListPage(criteria, bno), HttpStatus.OK);
    }

    @GetMapping("/{rno}")
    public ResponseEntity<ReplyVO> get(@PathVariable Long rno){
        log.info("get : " + rno);
        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable Long rno){
        log.info("remove : "+ rno);

        return service.remove(rno) == 1 ? new ResponseEntity<String>("Success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@PathVariable Long rno, @RequestBody ReplyVO vo){

        vo.setRno(rno);
        log.info("Modify rno : " + rno);
        log.info("Modify vo : " + vo);
        return service.modify(vo) == 1 ? new ResponseEntity<String>("Success", HttpStatus.OK)
                : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
