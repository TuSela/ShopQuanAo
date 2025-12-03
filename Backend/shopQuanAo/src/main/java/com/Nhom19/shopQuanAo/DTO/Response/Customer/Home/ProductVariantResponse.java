package com.Nhom19.shopQuanAo.DTO.Response.Customer.Home;

import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private int maBienThe;
    private ProductSizes maKc;
    private ProductColors maMs;
    private int soluong;
    private boolean trangThai;
    private String urlImage;
    private boolean daiDien;
}
