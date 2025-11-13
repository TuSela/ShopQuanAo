package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTk;
    private String username;
    private String password;
    private String hoten;
    private String email;
    private String sdt;
    private String diachi;

}
