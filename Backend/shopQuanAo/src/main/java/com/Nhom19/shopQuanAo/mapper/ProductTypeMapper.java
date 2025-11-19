package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypes toProductTypes(TypeCreationRequest request);
}
