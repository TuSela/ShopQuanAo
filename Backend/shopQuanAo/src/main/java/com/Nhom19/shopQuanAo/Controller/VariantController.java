package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/variants")
public class VariantController {
    @Autowired
    ProductVariantService productVariantService;

    @GetMapping("/{maSp}")
    public ApiResponse<List<ProductVariantResponse>> getByMaSp(
            @PathVariable Integer maSp) {
        ApiResponse<List<ProductVariantResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productVariantService.getVariantsByMaSp(maSp));
        return apiResponse;
    }
}
