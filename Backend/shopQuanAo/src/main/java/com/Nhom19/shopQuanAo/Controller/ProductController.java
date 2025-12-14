package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ProductDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
//    @GetMapping()
//    public ApiResponse<List<ProductResponse>> showHomePage(){
//        Products Product =new Products();
//
//        ApiResponse<List<ProductResponse>> apiResponse =new ApiResponse();
//        apiResponse.setResult(productService.getProducts());
//        return apiResponse;
//    }
    @GetMapping("/{maSp}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable("maSp") int maSp){
        ApiResponse<ProductDetailResponse> apiResponse =new ApiResponse();
        apiResponse.setResult(productService.getProductDetail(maSp));
        return apiResponse;
    }
    @GetMapping()
    public ApiResponse<List<ProductBestSellerResponse>> getProducts10(){
        ApiResponse<List<ProductBestSellerResponse>> apiResponse = new ApiResponse();
        List<ProductBestSellerResponse> productBestSellerResponseList =productService.getTopBestSeller();
        apiResponse.setResult(productBestSellerResponseList);
        return apiResponse;
    }
    @PostMapping
    public ApiResponse<Boolean> addProduct(@RequestBody CreationProductRequest request){
        ApiResponse<Boolean> apiResponse = new ApiResponse();
        apiResponse.setResult(productService.createProduct(request));
        return apiResponse;
    }
}
