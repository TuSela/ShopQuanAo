package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductResponse;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetailResponse;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toDTO(Products products);
    ProductDetailResponse toDTO2(Products products);
    ProductVariantResponse toDTO3(ProductVariants productVariants);
}
