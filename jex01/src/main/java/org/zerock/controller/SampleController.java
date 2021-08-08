package org.zerock.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }



    @RequestMapping("")
    public void basic(){
        log.info("basic ..............");
    }
    @RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
    public void basicGet(){
        log.info("basic get.................");
    }
    @GetMapping("/basicGetOnly")
    public void basicGet2(){
        log.info("basic get only .................");
    }

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto, RedirectAttributes attributes){
        log.info("" + dto);
        return "ex01";
    }

    @GetMapping("/ex02")
    public String ex02(String name, int age){
        log.info("name : " + name);
        log.info("age : " + age);
        return "ex02";
    }

    @GetMapping("/ex02List")
    public String ex02List(@RequestParam ArrayList<String> ids){
        log.info("ids : " + ids);

        return "ex02List";
    }
    @GetMapping("/ex02Array")
    public String ex02Array(String[] ids){
        log.info("ids : " + Arrays.toString(ids));

        return "ex02Array";
    }

    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list){
        log.info("list dto : " + list);
        return "ex02Bean";
    }

    @GetMapping("/ex03")
    public String ex03(TodoDTO todo){
        log.info("todo : " + todo);
        return "ex03";
    }

    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page){
        log.info("dto : " + dto);
        log.info("page : " + page);
        return "/sample/ex04";
    }

    @GetMapping("/ex05")
    public void ex05(){
        log.info("/ex05...............");
    }

    @GetMapping(value = "/ex06")
    @ResponseBody
    public SampleDTO ex06(){
        log.info("/ex06.................");
        SampleDTO dto = new SampleDTO();
        dto.setName("ABC");
        dto.setAge(15);
        return dto;
    }

    @GetMapping("/ex07")
    public ResponseEntity<SampleDTO> ex07(){
        log.info("/ex07..................");
        SampleDTO dto = new SampleDTO();
        dto.setAge(10);
        dto.setName("Good");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<SampleDTO>(dto, headers, HttpStatus.OK);
    }

    @GetMapping("/exUpload")
    public void exUpload(){
        log.info("/exUpload ........................");
    }

    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files){
        files.forEach(file ->{
            log.info("---------------------------------------");
            log.info("name : " + file.getOriginalFilename());
            log.info("size : " + file.getSize());
        });
    }
}
