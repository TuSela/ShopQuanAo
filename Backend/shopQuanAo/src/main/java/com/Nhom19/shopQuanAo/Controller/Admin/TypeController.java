package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.NavMenuDTO;
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
    @GetMapping("/nav-menu")
    public ApiResponse<List<NavMenuDTO>> getNavMenu() {

        ApiResponse<List<NavMenuDTO>> response = new ApiResponse<>();
        response.setResult(productTypeService.buildNavMenu());

        return response;
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

    @PutMapping("/{maLoai}")
    public ApiResponse<ProductTypeResponse> updateProductType (
            @RequestBody TypeCreationRequest request,
            @PathVariable("maLoai") int maLoai) {
        ApiResponse<ProductTypeResponse> productTypeResponse = new ApiResponse<>();
        productTypeResponse.setResult(productTypeService.updateProductType(maLoai, request));
        return productTypeResponse;
    }

    @DeleteMapping("/{maLoai}")
    public ApiResponse<Boolean> deleteProductType(@PathVariable("maLoai") int maLoai) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        var result = productTypeService.deleteProductType(maLoai);
        if (result) {
            response.setResult(true);
        } else  {
            response.setResult(false);
        }
        return response;
    }

    @PutMapping("/{maLoai}/enable")
    public ApiResponse<String> enableType(@PathVariable("maLoau")  int maLoai) {
        ApiResponse<String> response = new ApiResponse<>();
        productTypeService.enableType(maLoai);
        response.setResult("Loại sản phẩm đã được kích hoạt!");
        return response;
    }
    @PutMapping("/{maLoai}/disable")
    public ApiResponse<String> disableType(@PathVariable("maLoai")  int maLoai) {
        ApiResponse<String> response = new ApiResponse<>();
        productTypeService.disableType(maLoai);
        response.setMessage("Loại sản phẩm đã bị vô hiệu hóa");
        return response;
    }
}

