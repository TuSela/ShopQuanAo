package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/types")
@RestController
public class TypeController {
    @Autowired
    ProductTypeService productTypeService;
    @GetMapping
    public ApiResponse<List<ProductTypeResponse>> getALLTypes (){
        ApiResponse<List<ProductTypeResponse>> listProductTypes = new ApiResponse<>();
        listProductTypes.setResult(productTypeService.getTypes());
       return listProductTypes;
    }

    @GetMapping("/{maLoai}")
    public ApiResponse<ProductTypeResponse> getProductType (@PathVariable("maLoai") int maLoai) {
        ApiResponse<ProductTypeResponse> productTypeResponse = new ApiResponse<>();
        productTypeResponse.setResult(productTypeService.getProductType(maLoai));
        return productTypeResponse;
    }

    @PostMapping
    public ApiResponse<ProductTypeResponse> createProductType(
            @RequestBody TypeCreationRequest  typeCreationRequest,
            UriComponentsBuilder uriBuilder) {
        ApiResponse<ProductTypeResponse> productTypeResponse = new ApiResponse<>();
        productTypeResponse.setResult(productTypeService.addProductType(typeCreationRequest));
        return productTypeResponse;
    }


}
