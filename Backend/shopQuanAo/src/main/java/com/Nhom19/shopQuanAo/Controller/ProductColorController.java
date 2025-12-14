package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/colors")
@RestController
public class ProductColorController {
    @Autowired
    private ProductColorService productColorService;
    @GetMapping
    public ApiResponse<List<ColorResponse>> getAllProductColors()
    {
        ApiResponse<List<ColorResponse>> productColors = new ApiResponse<>();
        productColors.setResult(productColorService.getAllProductColors());
        return productColors;
    }
}
