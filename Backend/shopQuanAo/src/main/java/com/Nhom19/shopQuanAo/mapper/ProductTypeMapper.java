package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductSizeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV.ChiTietLoaiResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.ColorResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypes toProductTypes(TypeCreationRequest request);
    ProductTypeResponse toProductTypeResponse(ProductTypes productTypes);
    ColorResponse toColorResponse(ProductColors productColors);

    ProductSizeResponse toProductSizeResponse(ProductSizes productSizes);
    ChiTietLoaiResponse toChiTietLoaiResponse(ProductTypes productTypes);

    void updateProductTypes(@MappingTarget ProductTypes productTypes, TypeCreationRequest request);
}
