package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductVariantResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductDetailResponse {
    private int maSp;
    private String tenSp;
    private int soLuong;
    private BigDecimal gia;
    private String chiTiet;
    private float danhGia;
    private Integer soLuongDanhGia;
    private Set<ProductVariantResponse>  productVariants = new HashSet<>();
    private Set<ProductCommentResponse> productComments = new HashSet<>();
}
