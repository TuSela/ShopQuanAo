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
public class ProductComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaBl;
    private int MaTk;
    private int MaSp;
    private LocalDateTime NgayTao;
    private float DanhGia;
    private String NoiDung;
    private String TrangThai;
}
