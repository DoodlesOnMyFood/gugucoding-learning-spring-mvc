package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

    @GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
    public String getText(){
        log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
        return "안녕하세요";
    }

    @GetMapping(value = "/getSample")
    public SampleVO getSample(){
        return new SampleVO(112, "스타", "로드");

    }

    @GetMapping("/getList")
    public List<SampleVO> getSamples(){
        return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "first", i + "last")).collect(Collectors.toList());
    }

    @GetMapping("/getMap")
    public Map<String, SampleVO> getMap(){
        Map<String, SampleVO> map = new HashMap<>();
        map.put("First", new SampleVO(111, "그루트", "주니어"));
        return map;
    }

    @GetMapping(value = "/check", params = {"height", "weight"})
    public ResponseEntity<SampleVO> check(Double height, Double weight){
        SampleVO vo = new SampleVO(0, "" + height, weight.toString());

        ResponseEntity<SampleVO> response = null;

        if(height < 150){
            response = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        }else{
            response = ResponseEntity.ok().body(vo);
        }
        return  response;
    }

    @GetMapping("/product/{cat}/{pid}")
    public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid){
        return new String[]{"Category : " + cat, "productId : " + pid};
    }

    @PostMapping("/ticket")
    public Ticket ticket(@RequestBody Ticket ticket){
        log.info("convert.........ticket" + ticket);
        return ticket;
    }
}
