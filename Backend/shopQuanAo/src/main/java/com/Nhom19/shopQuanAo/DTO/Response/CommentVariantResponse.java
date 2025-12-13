package com.Nhom19.shopQuanAo.DTO.Response;

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
public class CommentVariantResponse {
    private int maBienThe;
    private int soLuongDat;
    private String tenKc;
    private String tenMs;
}
