package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes.OrderDetailResponse;
import com.Nhom19.shopQuanAo.DTO.Response.OrderDetailRes.PaymentResponse;
import com.Nhom19.shopQuanAo.entity.PaymentMethods;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toDTO(PaymentMethods paymentMethods);
}
