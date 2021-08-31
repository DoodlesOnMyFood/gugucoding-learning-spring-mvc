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

@Controller
@Log4j
public class UploadController {
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
        for(MultipartFile file : files){
            log.info("---------------------------------");
            log.info("Upload File Name : " + file.getOriginalFilename());
            log.info("Upload file size : " + file.getSize());

            File saveFile = new File(uploadFolder + "/" + file.getOriginalFilename());

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

        for(MultipartFile multipartFile : uploadFile){
            log.info("----------------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload file size : " + multipartFile.getSize());

            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

            try {
                multipartFile.transferTo(saveFile);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }


    }
}
