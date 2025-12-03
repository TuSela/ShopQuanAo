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
    @ManyToOne
    @JoinColumn(name = "ma_bien_the")
    private ProductVariants variants;
//    private int maBienThe;

    @Column(name = "url_hinh_anh")
    private String urlImage;

    private Boolean daiDien;
}
