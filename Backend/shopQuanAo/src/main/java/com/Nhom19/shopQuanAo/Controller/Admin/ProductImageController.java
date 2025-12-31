package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @PostMapping()
    public ApiResponse<Boolean> CreateProductImages(@RequestBody CreationProductImageRequest creationProductImageRequest) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setResult(productImageService.createProductImageRequest(creationProductImageRequest));
        return response;
    }
}
