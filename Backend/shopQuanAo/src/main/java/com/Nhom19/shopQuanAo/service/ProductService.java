package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Products> getProducts(){
       return productRepository.findAll();
    }
}
