package com.Nhom19.shopQuanAo.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CapNhatUserRequest {
    @Size(min = 3, max = 50)
    private String username;
    @Size(min = 3, max = 50)
    private String password;
    private String hoten;
    private String email;
    private String sdt;
    private String diachi;
}
