package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.ProductColors;
import com.Nhom19.shopQuanAo.entity.ProductSizes;
import com.Nhom19.shopQuanAo.entity.Products;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Products product;

    public Integer getMaSp() {
        return product != null ? product.getMaSp() : null;
    }

    @JsonIgnore
    private ProductColors colors;

    public Integer getMaMs() {
        return colors != null ? colors.getMaMs() : null;
    }

    public String getTenMs() {
        return colors != null ? colors.getTenMs() : null;
    }

    @JsonIgnore
    private ProductSizes sizes;

    public Integer getMaKc() {
        return sizes != null ? sizes.getMaKc() : null;
    }

    public String getTenKc() {
        return sizes != null ? sizes.getTenKc() : null;
    }


}
