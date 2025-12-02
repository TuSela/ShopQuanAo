package com.Nhom19.shopQuanAo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Entity
public class cart {
    @Id
    private int MaGh;
    @ManyToOne
    @JoinColumn(name = "maTk")
    private Users maTk;

    private LocalDateTime NgayTao;
    private LocalDateTime NgaySua;
    private float TongTien;
}
