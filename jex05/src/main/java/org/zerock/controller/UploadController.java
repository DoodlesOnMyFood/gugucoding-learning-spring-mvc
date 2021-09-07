package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private boolean checkImageType(File file){
        try {
            String contentType = Files.probeContentType(file.toPath());

            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
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

    @PostMapping(name = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile){
        List<AttachFileDTO> list = new ArrayList<>();
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
            AttachFileDTO attachFileDTO = new AttachFileDTO();
            attachFileDTO.setFileName(multipartFile.getOriginalFilename());
            UUID uuid = UUID.randomUUID();
            attachFileDTO.setUuid(uuid.toString());
            attachFileDTO.setUploadPath(getFolder());
            String uploadFileName = uuid + "_" + multipartFile.getOriginalFilename();
            File saveFile = new File(uploadPath, uploadFileName);

            try {
                multipartFile.transferTo(saveFile);
                if (checkImageType(saveFile)){
                    attachFileDTO.setImage(true);
                    FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" +uploadFileName));
                    Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
                    thumbnail.close();
                }
                list.add(attachFileDTO);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
    }
}
