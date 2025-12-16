package com.Nhom19.shopQuanAo.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maHa;

    @Column(name = "url_hinh_anh")
    private String urlImage;

    private Boolean daiDien;
    private Boolean daiDienMau;
    @ManyToOne
    @JoinColumn(name = "ma_sp")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "ma_ms")
    private ProductColors productColor;

}
