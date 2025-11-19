package com.Nhom19.shopQuanAo.DTO.Response.Customer.Home;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductTypeResponse {
//    private int maLoai;
    private String tenLoai;
//    private String tinhTrang;
//    @OneToMany(mappedBy = "types")
//    private Set<Products> products = new HashSet<>();
}
