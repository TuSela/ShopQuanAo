package com.Nhom19.shopQuanAo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HangQuanAo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MaH;
    private String TenH;
    private String Email;
    private  String SDT;
    private  String Diachi;
}
