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
    private int MaDiaChi;

    private int maTk;

    private String Hoten;
    private String Sdt;
    private String Email;
    private String TinhThanhPho;
    private String PhuongXa;
    private String QuanHuyen;
    private String DiaChi;
}
