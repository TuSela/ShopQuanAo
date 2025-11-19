package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping()
    public ApiResponse<List<ProductResponse>> showHomePage(){
        Products Product =new Products();

        ApiResponse<List<ProductResponse>> apiResponse =new ApiResponse();
        apiResponse.setResult(productService.getProducts());
        return apiResponse;
    }
//    @PostMapping
//    public ApiResponse<List<ProductResponse>> addProduct(@RequestBody Pro product){}
}
