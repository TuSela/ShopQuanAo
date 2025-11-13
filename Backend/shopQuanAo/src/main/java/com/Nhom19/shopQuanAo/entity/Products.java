package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaSp;
    private int MaLoai;
    private int MaHang;
    private String TenSp;
    private int SoLuong;
    private BigDecimal Gia;
    private String ChiTiet;
    private float DanhGia;
}
