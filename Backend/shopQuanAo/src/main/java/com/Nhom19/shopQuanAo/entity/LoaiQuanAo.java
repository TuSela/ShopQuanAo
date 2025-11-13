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
public class LoaiQuanAo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Mal;
    private String Tenl;
    private String TinhTrang;
}
