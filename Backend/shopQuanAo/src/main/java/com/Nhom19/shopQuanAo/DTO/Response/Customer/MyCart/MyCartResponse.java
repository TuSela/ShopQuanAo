package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder.MyOrderItemResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MyCartResponse {
    private Integer maGioHang;
    private BigDecimal tongTien;
    private List<MyCartItemResponse> items;
}
