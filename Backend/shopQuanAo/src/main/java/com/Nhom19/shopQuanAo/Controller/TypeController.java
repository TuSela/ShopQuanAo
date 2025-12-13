package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductDetail.ProductTypeResponse;
import com.Nhom19.shopQuanAo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
