package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maSp;

//    @ManyToOne
//    @JoinColumn(name = "ma_loai")
//    private ProductTypes types;
    private int maLoai;
    private String tenSp;
    private int soLuong;
    private BigDecimal gia;
    private String chiTiet;
    private float danhGia;

//    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProductVariants> productVariants;
//
//    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProductComments> ProductComments;

}
