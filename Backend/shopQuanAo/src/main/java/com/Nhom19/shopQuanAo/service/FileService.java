package com.Nhom19.shopQuanAo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    public String uploadFileImg(MultipartFile file) throws IOException {
        
        // Validate
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File không phải dạng ảnh");
        }

        // Tạo folder
        String folder = "D:/shopbanquanao/public/";
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();

        // Tạo tên file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Lưu file
        Path path = Paths.get(folder + fileName);
        Files.copy(file.getInputStream(), path);

        // Trả về URL
        return "http://localhost:8081/nhom19/public/" + fileName;
    }
}
