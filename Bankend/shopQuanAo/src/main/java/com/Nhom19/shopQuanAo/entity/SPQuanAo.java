package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@Table(name = "SPQuanAo")
public class SPQuanAo {
    @Id
    @Column(name = "MaSP")
    private int MaSP;

    @Column(name = "Mal")
    private int Mal;

    @Column(name = "MaH")
    private int MaH;

    @Column(name = "TenSP")
    private String TenSP;

    @Column(name = "Soluong")
    private int Soluong;

    @Column(name = "Gia")
    private BigDecimal Gia;

    @Column(name = "ChiTietSP")
    private String ChiTietSP;

    @Column(name = "DanhGia")
    private float DanhGia;
}
