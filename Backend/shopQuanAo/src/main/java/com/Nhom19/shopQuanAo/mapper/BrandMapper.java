package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.BrandCreationRequest;
import com.Nhom19.shopQuanAo.entity.Brands;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brands toBrand(BrandCreationRequest request);
}
