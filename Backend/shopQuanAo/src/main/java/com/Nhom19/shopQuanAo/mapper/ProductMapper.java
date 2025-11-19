package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.BrandResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.Brands;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.entity.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toDTO(Products products);
    ProductTypeResponse toDTO(ProductTypes productTypes);
    BrandResponse toDTO(Brands brands);
}
