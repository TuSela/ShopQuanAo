package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyCartItemResponse {
    private String tenSanPham;
    private String anh;
    private String mau;
    private String size;
    private Integer soLuong;
    private BigDecimal gia;
}
