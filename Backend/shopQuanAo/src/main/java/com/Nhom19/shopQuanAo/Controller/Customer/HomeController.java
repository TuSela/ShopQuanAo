package com.Nhom19.shopQuanAo.Controller.Customer;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetailResponse;
import com.Nhom19.shopQuanAo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/home")
@RestController
public class HomeController {
@Autowired
private ProductService sanPhamService;
    @GetMapping()
    public ApiResponse<List<ProductBestSellerResponse>> showHomePage(){
        ApiResponse<List<ProductBestSellerResponse>> apiResponse =new ApiResponse<>();
        apiResponse.setResult(sanPhamService.getTopBestSeller());
        return apiResponse;
    }
    @GetMapping("/{maSp}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable("maSp") int maSp){
        ApiResponse<ProductDetailResponse> apiResponse =new ApiResponse();
        apiResponse.setResult(sanPhamService.getProductDetail(maSp));
        return apiResponse;
    }
    
}
