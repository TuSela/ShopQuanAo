package com.Nhom19.shopQuanAo.DTO.Response.Customer.Home;

import com.Nhom19.shopQuanAo.entity.ProductVariants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
public class ProductResponse {
        private int MaSp;

        private ProductTypeResponse types;

        private BrandResponse brands;

        private String tenSp;
        private int soLuong;
        private BigDecimal gia;
        private String chiTiet;
        private float danhGia;

        private Set<ProductVariants> mabienthe;
//        private Set<ProductComments> ProductComments;
}
