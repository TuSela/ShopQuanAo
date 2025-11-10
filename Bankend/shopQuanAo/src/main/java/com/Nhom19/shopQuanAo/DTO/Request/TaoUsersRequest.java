package com.Nhom19.shopQuanAo.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaoUsersRequest {
    @Size(min = 3, max = 50)
    private String matkhau;
    @Size(min = 3, max = 50)
    private String tentk;
    private String email;
    private String sdt;     // nếu muốn giữ tên JSON giống "SDT" thì ok
    private String diachi;
    private String hovaten;
}
