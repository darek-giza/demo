package com.giza.dariusz.demo.controller;

import com.giza.dariusz.demo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping
    @RequestMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart(value = "file") MultipartFile file) throws Exception {
        String name = fileUploadService.storeFileInHome(file);
        return new ResponseEntity<>(name, HttpStatus.CREATED);
    }

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
