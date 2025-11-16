package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaBienThe;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_sp")
    private Products products;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ma_ms")
    private ProductColors colors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="ma_kc")
    private ProductSizes sizes;

    private int soluong;

    @OneToMany(mappedBy = "variants", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImages> images = new HashSet<>();

    @OneToMany(mappedBy = "variants", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrdersItems> ordersItems = new HashSet<>();
}
