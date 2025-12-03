package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maTk;
    private String username;
    private String password;
    @Column(name = "ma_quanly")
    private String managerCode;
}
