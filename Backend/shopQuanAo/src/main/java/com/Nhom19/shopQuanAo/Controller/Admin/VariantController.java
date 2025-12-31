package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateVariantRequest;
import com.Nhom19.shopQuanAo.DTO.Request.Admin.UpdateVariantRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{maBienThe}")
    public ApiResponse<Boolean> UpdateVariant (@RequestBody UpdateVariantRequest request, @PathVariable("maBienThe") Integer maBienThe){
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.UpdateVariant(request,maBienThe));
        return apiResponse;
    }
    @PostMapping("/{maSp}")
    public ApiResponse<List<ProductVariantResponse>> createVariant(@RequestBody CreateVariantRequest request,@PathVariable("maSp")Integer maSp){
        ApiResponse<List<ProductVariantResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productVariantService.createVariants(request,maSp));
        return apiResponse;
    }
}
