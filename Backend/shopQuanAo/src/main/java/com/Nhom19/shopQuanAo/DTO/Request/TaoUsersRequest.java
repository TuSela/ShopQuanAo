package com.Nhom19.shopQuanAo.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaoUsersRequest {
    @Size(min = 3, max = 50)
    private String password;
    @Size(min = 3, max = 50)
    private String username;
    private String email;
    private String sdt;
    private String diachi;
    private String hoten;
}
