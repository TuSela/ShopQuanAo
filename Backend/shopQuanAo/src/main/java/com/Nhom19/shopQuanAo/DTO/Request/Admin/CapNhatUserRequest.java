package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapNhatUserRequest {
    private String hoten;
    private String email;
    private String sdt;
    private String gioiTinh;
    private String ngaySinh;


}
