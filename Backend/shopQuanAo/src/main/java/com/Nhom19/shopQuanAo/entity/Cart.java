package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @Column(name = "ma_gh")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maGh;

    @ManyToOne
    @JoinColumn(name = "maTk")
    private Users users;

    private LocalDateTime ngayTao;

    private LocalDateTime ngaySua;
    private BigDecimal tongTien;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems ;
}
