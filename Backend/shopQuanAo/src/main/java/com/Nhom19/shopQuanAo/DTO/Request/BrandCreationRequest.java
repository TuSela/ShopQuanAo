package com.Nhom19.shopQuanAo.DTO.Request;

import com.Nhom19.shopQuanAo.entity.Products;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class BrandCreationRequest {
    private String tenHang;
    private String email;
    private String sdt;
    private String diaChi;
}
