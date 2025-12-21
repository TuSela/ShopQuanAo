package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreationProductRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductResponse2;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.CommentVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ProductDetailResponse;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse2 toDTO(Products products);
    ProductDetailResponse toDTO2(Products products);
    ProductVariantResponse toDTO3(ProductVariants productVariants);
    CreationProductRequest toDTO4(Products products);
    Products toEntity(CreationProductRequest request);
    ProductBestSellerResponse toDTO5(Products products);
    CommentVariantResponse toDTO6(ProductVariants productVariants);
}
