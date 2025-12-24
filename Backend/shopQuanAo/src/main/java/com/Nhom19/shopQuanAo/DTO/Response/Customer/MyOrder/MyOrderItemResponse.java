package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyOrder;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MyOrderItemResponse {
    private String tenSanPham;
    private Integer maBienThe;
    private String anh;
    private String mau;
    private String size;
    private Integer soLuong;
    private BigDecimal gia;
}
