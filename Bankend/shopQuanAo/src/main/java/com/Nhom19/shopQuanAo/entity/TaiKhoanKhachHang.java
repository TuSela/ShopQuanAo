package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TaiKhoanKhachHang")
public class TaiKhoanKhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTK")
    private Integer maTK;

    @Column(name = "TenTK", nullable = false)
    private String tenTK;

    @Column(name = "Email")
    private String email;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DiaChi", nullable = false)
    private String diaChi;

    @Column(name = "MatKhau", nullable = false)
    private String matKhau;

    @Column(name = "HovaTen")
    private String hovaTen;
}
