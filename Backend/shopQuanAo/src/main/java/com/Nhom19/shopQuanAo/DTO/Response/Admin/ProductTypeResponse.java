package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTypeResponse {
    private int maLoai;
    private String tenLoai;
    private String chiTietLoai;
    private String doiTuong;
    private String anhLoai;
    private String tinhTrang;

//    @OneToMany(mappedBy = "types")
//    private Set<Products> products = new HashSet<>();
}
