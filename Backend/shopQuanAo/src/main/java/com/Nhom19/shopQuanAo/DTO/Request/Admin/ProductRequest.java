package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import com.Nhom19.shopQuanAo.entity.ProductComments;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductRequest {
        private int MaSp;

        private ProductTypes types;


        private String tenSp;
        private int soLuong;
        private BigDecimal gia;
        private String chiTiet;
        private float danhGia;

        private Set<ProductVariants> ProductVariants;
        private Set<ProductComments> ProductComments;
}
