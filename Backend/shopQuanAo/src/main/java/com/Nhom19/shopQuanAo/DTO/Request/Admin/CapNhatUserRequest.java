package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapNhatUserRequest {
    private String hoten;
    private String email;
    private String sdt;
    private String gioiTinh;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngaySinh;
}
