package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.entity.ProductTypes;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreationProductRequest {
    private Integer maSp;
    private Integer maLoai;
    private String tenSp;
    private Integer soLuong;
    private BigDecimal gia;
    private String chiTiet;
    private List<ColorRequest> colors;
}
