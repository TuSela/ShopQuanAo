package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/files")
@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("/images")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "File không được để trống")
            );
        }

        try {
            String url = fileService.uploadFileImg(file);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Upload thành công",
                            "url", url
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("message", "Lỗi khi upload: " + e.getMessage())
            );
        }
    }
}
