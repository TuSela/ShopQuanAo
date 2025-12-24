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
    private Integer maLoai;
    private String anhDaiDien;
    private String tenSp;
    private int soLuong;
    private BigDecimal gia;
    private BigDecimal danhGia;
    private Integer soLuongDanhGia;
    private List<ColorResponse> Variants;
    private List<String> listAnhSP;
    private String chiTiet;
    private List<ProductResponse> productComments;

}
