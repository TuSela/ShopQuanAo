package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductSizeResponse;
import com.Nhom19.shopQuanAo.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/sizes")
@RestController
public class SizeController {
    @Autowired
    private ProductSizeService productSizeService;
    @GetMapping
    public ApiResponse<List<ProductSizeResponse>> getSize(){
        ApiResponse<List<ProductSizeResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productSizeService.getAllProductSize());
        return apiResponse;
    }
}
