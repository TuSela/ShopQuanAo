package com.Nhom19.shopQuanAo.DTO.Response.Customer.Home;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SPNamResponse {
    private int maSp;
    private String tenSp;
    private BigDecimal gia;
    private String urlImage;
}
