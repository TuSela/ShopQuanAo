package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDiaChi;

    private int maTk;

    private String hoten;
    private String sdt;
    private String email;
    private String tinhThanhPho;
    private String phuongXa;
    private String quanHuyen;
    private String diaChi;
}
