package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.CreateOrUpdateSizeRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductSizeResponse;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductSizeMapper {
    ProductSizeResponse toProductSizeResponse(ProductSizes productSizes);
    void updateSize(@MappingTarget ProductSizes productSizes, CreateOrUpdateSizeRequest request);
    ProductSizes createSize(CreateOrUpdateSizeRequest request);
}
