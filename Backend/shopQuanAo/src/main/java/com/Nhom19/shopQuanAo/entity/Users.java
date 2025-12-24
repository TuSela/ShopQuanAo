package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTk;
    private String password;
    private String hoten;
    private String email;
    private String sdt;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String avatar;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

    @OneToMany(mappedBy = "users")
    private List<ProductComments> comments;

    // feedbacks if needed
//    @OneToMany(mappedBy = "users")
//    private Set<Feedbacks> feedbacks = new HashSet<>();
}
