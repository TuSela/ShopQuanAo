package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommentRequest {
    private Integer maBienThe;
    private Integer maDdh;
    private String noiDung;
    private BigDecimal diemDanhGia;
}
