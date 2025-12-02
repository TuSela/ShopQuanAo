package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String hoten;
    private String email;
    private String sdt;
}
