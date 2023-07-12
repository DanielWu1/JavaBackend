package com.danielprojectdemo.backenddemo.controller;

import com.danielprojectdemo.backenddemo.pojo.Result;
import com.danielprojectdemo.backenddemo.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private EmpService empService;

    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
        log.info("file {}, {}, {}", username, age, image);

        String originalName = image.getOriginalFilename();
        int index = originalName.lastIndexOf(".");
        String netname = originalName.substring(index);
        String newName = UUID.randomUUID().toString() + netname;
        log.info("new file name {}", newName);

        String filePath = "/Users/daniel/Desktop/intelliJ/file/" + newName;
        image.transferTo(new File(filePath));
        return Result.success(newName);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // 调用ImageService获取图片资源
        Resource imageResource = empService.getImage(imageName);

        // 根据资源的内容类型返回合适的响应头
        String contentType = MimeTypeUtils.IMAGE_PNG_VALUE;// 根据图片类型设置合适的contentType
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        // 返回包含图片内容的ResponseEntity
        return new ResponseEntity<>(imageResource, headers, HttpStatus.OK);
    }
}
