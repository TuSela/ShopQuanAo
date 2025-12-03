package com.Nhom19.shopQuanAo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maKc;
    private String tenKc;
//    @OneToMany(mappedBy = "sizes", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProductVariants> variants = new HashSet<>();
}
