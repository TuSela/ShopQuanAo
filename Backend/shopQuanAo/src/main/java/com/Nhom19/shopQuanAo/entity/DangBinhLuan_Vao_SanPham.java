package com.Nhom19.shopQuanAo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class DangBinhLuan_Vao_SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaBL;
    private int MaTK;
    private int MaSP;
    private LocalDateTime NgayTao;
    private float DiemDanhGia;
    private String ND;
    private String TrangThaiBL;
}
