package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MyOrderResponse {
    private Integer maDonHang;
    private LocalDateTime ngayDat;
    private BigDecimal tongTien;
    private String trangThai;
    private List<MyOrderItemResponse> items;
}
