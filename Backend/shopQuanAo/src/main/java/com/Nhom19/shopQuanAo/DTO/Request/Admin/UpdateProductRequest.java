package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductRequest {
    private String tenSp;
    private Integer maLoai;
    private BigDecimal gia;
    private String chiTiet;
}
