package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Request.Admin.TypeCreationRequest;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ColorResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductSizeResponse;
import com.Nhom19.shopQuanAo.DTO.Response.ProductTypeResponse;
import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import org.mapstruct.Mapper;

import java.awt.*;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypes toProductTypes(TypeCreationRequest request);
    ProductTypeResponse toProductTypeResponse(ProductTypes productTypes);
    ColorResponse toColorResponse(ProductColors productColors);
    ProductSizeResponse toProductSizeResponse(ProductSizes productSizes);
}
