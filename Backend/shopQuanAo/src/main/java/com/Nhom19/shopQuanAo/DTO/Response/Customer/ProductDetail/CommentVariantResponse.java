package com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentVariantResponse {
    private Integer maBienThe;
    private int soLuongDat;
    private String tenKc;
    private String tenMs;
    private BigDecimal tongTien;
}
