package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.awt.*;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    public ColorResponse toDto(ProductColors productColors);
    public ProductColors toEntity(CreateOrUpdateColorRequest request);
    void updateColor(@MappingTarget ProductColors productColors, CreateOrUpdateColorRequest request);
}
