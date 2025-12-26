package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateColorRequest;
import com.Nhom19.shopQuanAo.DTO.Response.ApiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.service.ProductColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequestMapping("/colors")
@RestController
public class ProductColorController {
    @Autowired
    private ProductColorService productColorService;
    @GetMapping
    public ApiResponse<List<ColorResponse>> getAllProductColors()
    {
        ApiResponse<List<ColorResponse>> productColors = new ApiResponse<>();
        productColors.setResult(productColorService.getAllProductColors());
        return productColors;
    }

    @GetMapping("/{maMs}")
    public ApiResponse<ColorResponse> getColor(@PathVariable("maMs") int maMs) {
        ApiResponse<ColorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productColorService.getProductColor(maMs));
        return apiResponse;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(
            @RequestBody CreateOrUpdateColorRequest colorRequest,
            UriComponentsBuilder uriBuilder) {
        ApiResponse<ColorResponse> apiResponse = new ApiResponse<>();
        var color = productColorService.createProductColor(colorRequest);

        apiResponse.setResult(color);
        var uri =  uriBuilder.path("/colors/{maMs}").buildAndExpand(color.getMaMs()).toUri();
        return ResponseEntity.created(uri).body(apiResponse);
    }

    @PutMapping("/{maMs}")
    public ApiResponse<ColorResponse> updateColor(
            @RequestBody CreateOrUpdateColorRequest colorRequest
            ,@PathVariable("maMs") int maMs) {
        ApiResponse<ColorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productColorService.updateProductColor(maMs,  colorRequest));
        return apiResponse;
    }

    @DeleteMapping("/{maMs}")
    public ApiResponse deleteColor (@PathVariable int maMs) {
        ApiResponse apiResponse = new ApiResponse<>();
        var result = productColorService.deleteColor(maMs);

        if (result) {
            apiResponse.setResult(true);
        } else {
            apiResponse.setResult(false);
        }
        return apiResponse;
    }
}
