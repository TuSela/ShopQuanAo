package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.CommentVariantResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductVariantResponse;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface VariantMapper {
    ProductVariantResponse toDTO(ProductVariants productVariants);
    CommentVariantResponse toDTO2(ProductVariants productVariants);
}
