package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
