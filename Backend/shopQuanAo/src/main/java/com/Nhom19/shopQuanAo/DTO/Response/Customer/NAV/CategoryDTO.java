package com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV;

import lombok.Data;

import java.util.List;
@Data
public class CategoryDTO {
    private String tenLoai;
    private List<String> chiTietLoai;
}
