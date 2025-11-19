package com.Nhom19.shopQuanAo.DTO.Response.Customer.Home;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BrandResponse {
    @Id
//    private int maHang;
    private String tenHang;
//    @OneToMany(mappedBy = "brands")
//    private Set<Products> products = new HashSet<>();
}
