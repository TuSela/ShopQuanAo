package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Brands {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maHang;
    private String tenHang;
    private String email;
    private  String sdt;
    private  String diaChi;
//    @OneToMany(mappedBy = "brands")
//    private Set<Products> products = new HashSet<>();
}
