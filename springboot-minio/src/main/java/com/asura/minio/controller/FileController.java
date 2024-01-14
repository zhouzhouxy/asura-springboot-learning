package com.asura.minio.controller;

import com.asura.minio.service.FileStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author zzyx 2024/1/14
 */
@Controller
public class FileController {

    @Resource
    private FileStorageService fileStorageService;

    @GetMapping("/")
    public ModelAndView index() {
        System.out.println("index");
        return new ModelAndView("./uploadForm");
    }

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
//        fileStorageService.uploadFile(file);
        String imgUrl = fileStorageService.uploadFileReturnUrl(file);
        ModelAndView modelAndView = new ModelAndView("uploadForm");
        modelAndView.addObject("message", "File uploaded successfully!");
        modelAndView.addObject("imgUrl", imgUrl);
        return modelAndView;
    }

    @GetMapping("/delete/{fileName}")
    public ModelAndView deleteFile(@PathVariable String fileName) {
        fileStorageService.deleteFile(fileName);
        ModelAndView modelAndView = new ModelAndView("uploadForm");
        modelAndView.addObject("message", "File deleted successfully!");
        return modelAndView;
    }


}
