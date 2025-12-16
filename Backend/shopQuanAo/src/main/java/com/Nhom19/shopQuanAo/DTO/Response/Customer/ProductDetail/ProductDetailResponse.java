package com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductDetailResponse {
    private int maSp;
    private String anhDaiDien;
    private String tenSp;
    private int soLuong;
    private BigDecimal gia;
    private float danhGia;
    private Integer soLuongDanhGia;
    private Set<ColorResponse> Variants;
    private List<String> listAnhSP;
    private String chiTiet;
    private Set<ProductResponse> productComments = new HashSet<>();

}
