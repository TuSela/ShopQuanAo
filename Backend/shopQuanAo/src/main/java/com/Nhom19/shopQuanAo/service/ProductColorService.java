package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateColorRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.exception.ColorNotFoundException;
import com.Nhom19.shopQuanAo.exception.DuplicateColorException;
import com.Nhom19.shopQuanAo.mapper.ColorMapper;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductColorRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ColorResponse> getAllProductColors() {
        List<ProductColors> productColors = productColorRepo.findAll();
        return productColors.stream().map(productTypeMapper::toColorResponse).collect(Collectors.toList());
    }

    public ColorResponse getProductColor(int maMs) {
        var productColors = productColorRepo.findById(maMs).orElseThrow(ColorNotFoundException::new);
        return colorMapper.toDto(productColors);
    }

    public ColorResponse updateProductColor(int maMs, CreateOrUpdateColorRequest request){
        var productColors = productColorRepo.findById(maMs).orElseThrow(ColorNotFoundException::new);
        colorMapper.updateColor(productColors, request);
        productColorRepo.save(productColors);
        return colorMapper.toDto(productColors);
    }

    public boolean deleteColor(int maMs) {
        if (!productColorRepo.existsById(maMs)) {
            return false;
        }

        productColorRepo.deleteById(maMs);
        return true;
    }

    public ColorResponse createProductColor(CreateOrUpdateColorRequest request){
        if (productColorRepo.existsByTenMs(request.getTenMs())) {
            throw new DuplicateColorException();
        }

        var productColor = colorMapper.toEntity(request);
        productColorRepo.save(productColor);
        return colorMapper.toDto(productColor);
    }
}
