package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateColorRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.exception.AppException;
import com.Nhom19.shopQuanAo.exception.ErrorCode;
import com.Nhom19.shopQuanAo.mapper.ColorMapper;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductColorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductColorService {
    @Autowired
    private ColorMapper colorMapper;
    @Autowired
    private ProductColorRepo productColorRepo;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    @PreAuthorize("hasAuthority('COLOR_MANAGE')")
    public List<ColorResponse> getAllProductColors() {
        List<ProductColors> productColors = productColorRepo.findAll();
        return productColors.stream().map(productTypeMapper::toColorResponse).collect(Collectors.toList());
    }
    @PreAuthorize("hasAuthority('COLOR_MANAGE')")
    public ColorResponse getProductColor(int maMs) {
        var productColors = productColorRepo.findById(maMs).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_COLOR_NOT_FOUND));
        return colorMapper.toDto(productColors);
    }
    @PreAuthorize("hasAuthority('COLOR_MANAGE')")
    public ColorResponse updateProductColor(int maMs, CreateOrUpdateColorRequest request){
        var productColors = productColorRepo.findById(maMs).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_COLOR_NOT_FOUND));

        if (!productColors.getTenMs().equals(request.getTenMs())) {
            if (productColorRepo.existsByTenMs(request.getTenMs())) {
                throw new AppException(ErrorCode.PRODUCT_COLOR_EXISTED);
            }
        }
        colorMapper.updateColor(productColors, request);
        productColorRepo.save(productColors);
        return colorMapper.toDto(productColors);
    }
    @PreAuthorize("hasAuthority('COLOR_MANAGE')")
    public boolean deleteColor(int maMs) {
        if (!productColorRepo.existsById(maMs)) {
            throw new AppException(ErrorCode.PRODUCT_COLOR_EXISTED);
        }
        productColorRepo.deleteById(maMs);
        return true;
    }
    @PreAuthorize("hasAuthority('COLOR_MANAGE')")
    public ColorResponse createProductColor(CreateOrUpdateColorRequest request){
        if (productColorRepo.existsByTenMs(request.getTenMs())) {
            throw new RuntimeException();
        }

        var productColor = colorMapper.toEntity(request);
        productColorRepo.save(productColor);
        return colorMapper.toDto(productColor);
    }
}
