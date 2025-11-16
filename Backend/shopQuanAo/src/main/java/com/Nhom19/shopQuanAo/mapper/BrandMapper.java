package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.BrandCreationRequest;
import com.Nhom19.shopQuanAo.entity.Brands;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brands toBrand(BrandCreationRequest request);
}
