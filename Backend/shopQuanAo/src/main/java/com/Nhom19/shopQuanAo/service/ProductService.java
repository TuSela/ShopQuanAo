package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.mapper.ProductMapper;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

//    public List<ProductBestSellerResponse>getTopBestSeller(){
//        Pageable top10 = PageRequest.of(0,10);
//        return productRepository.getTopBestSeller(top10);
//    }
}
