package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;


    public List<ProductResponse> getProducts(){
       return   productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }
}
