package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@Log4j
public class UploadController {

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }


    @GetMapping("/uploadForm")
    public void uploadForm(){
        log.info("upload form");
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax(){
        log.info("upload ajax");
    }

    @PostMapping("/uploadFormAction")
    public void uploadFormPost(@RequestParam("uploadFile") MultipartFile[] files, Model model){
        String uploadFolder =  "/tmp/upload";

        File uploadPath = new File(uploadFolder, getFolder());
        log.info("upload path : " + uploadPath);

        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        for(MultipartFile file : files){
            log.info("---------------------------------");
            log.info("Upload File Name : " + file.getOriginalFilename());
            log.info("Upload file size : " + file.getSize());


            File saveFile = new File(uploadPath + "/" + file.getOriginalFilename());
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @PostMapping("/uploadAjaxAction")
    public void uploadAjaxPost(MultipartFile[] uploadFile){
        log.info("Ajax post");
        String uploadFolder = "/tmp/upload_ajax";
        File uploadPath = new File(uploadFolder, getFolder());
        log.info("upload path : " + uploadPath);

        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        for(MultipartFile multipartFile : uploadFile){
            log.info("----------------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload file size : " + multipartFile.getSize());

            UUID uuid = UUID.randomUUID();

            File saveFile = new File(uploadPath, uuid + "_" + multipartFile.getOriginalFilename());

            try {
                multipartFile.transferTo(saveFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }


    }
}
