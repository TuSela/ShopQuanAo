package com.Nhom19.shopQuanAo.mapper;

import com.Nhom19.shopQuanAo.DTO.Response.Admin.OrderResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.OrderResponseDTO;
import com.Nhom19.shopQuanAo.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
     OrderResponse toOrderResponse(Orders orders);
}
