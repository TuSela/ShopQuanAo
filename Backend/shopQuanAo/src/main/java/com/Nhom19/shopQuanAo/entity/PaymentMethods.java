package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class PaymentMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_pt")
    private Long maPt;

    @Column(name = "ten_pt")
    private String tenPt;

    @OneToMany(mappedBy = "paymentMethods")
    private Set<Orders> orders = new HashSet<>();
}
