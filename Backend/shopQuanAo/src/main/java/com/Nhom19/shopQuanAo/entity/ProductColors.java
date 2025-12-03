package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductColors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maMs;
    private String tenMs;
//    @OneToMany(mappedBy = "colors")
//    private Set<ProductVariants> variants = new HashSet<>();
}
