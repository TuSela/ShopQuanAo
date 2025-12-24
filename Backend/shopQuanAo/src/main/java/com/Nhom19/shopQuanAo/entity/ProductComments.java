package com.Nhom19.shopQuanAo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ProductComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maBl;

    @ManyToOne
    @JoinColumn(name="ma_ddh")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name="ma_bien_the")
    private ProductVariants productVariants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tk")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_sp")
    private Products products;

    private LocalDateTime ngayTao;

    private BigDecimal diemDanhGia;
    private String noiDung;
    private String trangThai;

}
