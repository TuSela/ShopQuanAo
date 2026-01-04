package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.ProductVariants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
public class ProductResponse2 {
        private String anhDaiDien;
        private int MaSp;
        private ProductTypeResponse types;
        private String tenSp;
        private int soLuong;
        private BigDecimal gia;
        private String chiTiet;
        private float danhGia;
        private Boolean trangThai;
        private Set<ProductVariants> mabienthe;
}
