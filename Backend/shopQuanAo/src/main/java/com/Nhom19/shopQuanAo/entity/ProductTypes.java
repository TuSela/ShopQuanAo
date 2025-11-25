package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ProductTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maLoai;
    private String tenLoai;
    private String doiTuong;
//    private String tinhTrang;

    @Column(name = "url_hinh_anh")
    private String urlImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dm_con")
    private DmCon dmCon;

//    @OneToMany(mappedBy = "types")
//    private Set<Products> products = new HashSet<>();
}
