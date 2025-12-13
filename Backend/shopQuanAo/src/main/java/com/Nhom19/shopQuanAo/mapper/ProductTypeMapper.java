package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ColorResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductDetail.ProductSizeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductDetail.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypes toProductTypes(TypeCreationRequest request);
    ProductTypeResponse toProductTypeResponse(ProductTypes productTypes);
    ColorResponse toColorResponse(ProductColors productColors);
    ProductSizeResponse toProductSizeResponse(ProductSizes productSizes);
}
