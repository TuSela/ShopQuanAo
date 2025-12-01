package com.Nhom19.shopQuanAo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ProductComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaBl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_tk")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_sp")
    private Products products;

    private LocalDateTime NgayTao;

    private float DiemDanhGia;
    private String noiDung;
    private String trangThai;
}
