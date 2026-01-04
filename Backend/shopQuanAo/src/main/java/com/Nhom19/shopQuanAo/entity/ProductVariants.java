package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maBienThe;

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

    private boolean trangThai;
    @OneToMany(mappedBy = "productVariants", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> ordersItems;

    @OneToMany(mappedBy = "productVariants", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductComments> productComments;

}
