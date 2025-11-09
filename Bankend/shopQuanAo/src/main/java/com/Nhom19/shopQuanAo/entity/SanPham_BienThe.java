package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SanPham_BienThe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaBienThe;
    private int MaSP;
    private int MaMS;
    private int MaKC;
    private int Soluong;
}
