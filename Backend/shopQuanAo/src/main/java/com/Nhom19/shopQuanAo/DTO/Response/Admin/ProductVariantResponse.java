package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private int maBienThe;
    private int soluong;
    private boolean trangThai;
    private String urlImage;
    private boolean daiDien;
    private ProductColors colors;
    private ProductSizes sizes;
}
