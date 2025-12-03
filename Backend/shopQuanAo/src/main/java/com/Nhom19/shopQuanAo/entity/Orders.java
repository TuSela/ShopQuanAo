package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity

    @Getter
    @Setter
    public class Orders {
        @Id
        private int maDdh;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ma_pt")
        private PaymentMethods paymentMethods;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ma_tk")
        private Users users;

        @Column(name = "ngay_thanh_toan")
        private LocalDateTime paidAt;

        @Column(name = "ngay_van_chuyen")
        private LocalDateTime shippedAt;

        @Column(name = "tt_thanh_toan")
        private String paymentStatus;

        @Column(name = "tt_don_hang")
        private String orderStatus;

        private int maDiaChi;

        private float tongTien;

        @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<OrderItems> items = new HashSet<>();
    }
