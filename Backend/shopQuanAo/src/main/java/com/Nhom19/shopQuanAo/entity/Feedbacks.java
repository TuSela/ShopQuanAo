package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Feedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaPh;
    @ManyToOne
    @JoinColumn(name = "ma_tk")
    private Users users;

    @Column(name = "ngay_tao")
    private LocalDateTime createdAt;

    private String tieuDe;

    private String noiDung;

}
