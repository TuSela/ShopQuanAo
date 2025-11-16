package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "users")
    private Set<Orders> orders = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<ProductComments> comments = new HashSet<>();

    // feedbacks if needed
    @OneToMany(mappedBy = "users")
    private Set<Feedbacks> feedbacks = new HashSet<>();
}
