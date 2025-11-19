package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.BrandCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.entity.Brands;
import com.Nhom19.shopQuanAo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product/type")
public class TypeController {
    @Autowired
    private BrandService brandService;
    @PostMapping
    public ApiResponse<Brands> addBrand(@RequestBody BrandCreationRequest request) {
        ApiResponse<Brands> apiResponse = new ApiResponse<>();
        System.out.println("ket qua request: "+ request.getTenHang() +request.getDiaChi()+ request.getEmail() + request.getSdt());
        apiResponse.setResult(brandService.createBrand(request));
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<Brands>> getBrand() {
        List<Brands> brands = brandService.getAllBrands();
        ApiResponse<List<Brands>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(brands);
        return apiResponse;
    }
}
