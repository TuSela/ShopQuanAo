package com.Nhom19.shopQuanAo.DTO.Response.Customer.NAV;

import lombok.Data;

import java.util.List;
@Data
public class NavMenuDTO {
    private String doiTuong;
    private List<CategoryDTO> categories;
}
