package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.ProductSizeResponse;
import com.Nhom19.shopQuanAo.mapper.ProductTypeMapper;
import com.Nhom19.shopQuanAo.repository.ProductSizeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeService {
    @Autowired
    private ProductSizeRepo productSizeRepo;
    @Autowired
    private ProductTypeMapper productTypeMapper;
    public List<ProductSizeResponse>  getAllProductSize()
    {
        List<ProductSizeResponse> productSizeResponses = new ArrayList<>();
        return productSizeRepo.findAll()
                .stream()
                .map(productTypeMapper::toProductSizeResponse)
                .collect(Collectors.toList());
    }
}
