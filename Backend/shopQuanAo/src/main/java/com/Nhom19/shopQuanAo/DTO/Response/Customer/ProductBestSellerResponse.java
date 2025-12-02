package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBestSellerResponse {
    private String maSp;
    private String tenSp;
    private BigDecimal gia;
    private String urlImage;
    private Long totalQuantity;
}
