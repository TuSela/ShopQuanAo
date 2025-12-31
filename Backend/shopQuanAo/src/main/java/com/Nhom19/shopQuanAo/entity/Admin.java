package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maTk;
    private String username;
    private String password;

    @Column(name = "ma_quanly")
    private Integer managerCode = 1;
    private Boolean trangThai = true;
    @ManyToMany
    @JoinTable(
            name = "admins_roles",
            joinColumns = @JoinColumn(name = "admin_ma_tk"),          // FK tới admins
            inverseJoinColumns = @JoinColumn(name = "role_name") // FK tới role
    )
    private Set<Role> roles;
}
