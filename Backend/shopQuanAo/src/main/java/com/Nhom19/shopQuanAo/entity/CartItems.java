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
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "MaGh")
    private cart MaGh;
    @ManyToOne
    @JoinColumn(name = "MaBienThe")
    private ProductVariants variants;
    private int Soluong;
    private float TongTien;
}
