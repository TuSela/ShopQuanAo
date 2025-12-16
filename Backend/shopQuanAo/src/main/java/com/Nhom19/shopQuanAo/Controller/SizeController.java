package com.Nhom19.shopQuanAo.Controller;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateSizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductSizeResponse;
import com.Nhom19.shopQuanAo.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/sizes")
@RestController
public class SizeController {
    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping
    public ApiResponse<List<ProductSizeResponse>> getSize(){
        ApiResponse<List<ProductSizeResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productSizeService.getAllProductSize());
        return apiResponse;
    }

    @GetMapping("/{maKc}")
    public ApiResponse<ProductSizeResponse> getProductSize(@PathVariable("maKc") int maKc){
        ApiResponse<ProductSizeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productSizeService.getProductSize(maKc));
        return apiResponse;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductSizeResponse>> createSize(
            @RequestBody CreateOrUpdateSizeRequest request,
            UriComponentsBuilder uriBuilder){
        ApiResponse<ProductSizeResponse> apiResponse = new ApiResponse<>();
        var size = productSizeService.createSize(request);

        apiResponse.setResult(size);
        var uri = uriBuilder.path("/sizes/{maKc}").buildAndExpand(size.getMaKc()).toUri();
        return ResponseEntity.created(uri).body(apiResponse);
    }

    @PutMapping("/{maKc}")
    public ApiResponse<ProductSizeResponse> updateSize(
            @RequestBody CreateOrUpdateSizeRequest request,
            @PathVariable("maKc") int maKc){
        ApiResponse<ProductSizeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productSizeService.updateSize(maKc, request));
        return apiResponse;
    }

    public ApiResponse deleteSize(@PathVariable("maKc") int maKc){
        ApiResponse apiResponse = new ApiResponse();
        var result = productSizeService.deleteSize(maKc);
        if (result) {
            apiResponse.setResult(true);
        } else {
            apiResponse.setResult(false);
        }
        return apiResponse;
    }
}
