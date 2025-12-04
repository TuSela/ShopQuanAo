package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaoUsersRequest {
    @Size(min = 3, max = 50)
    private String password;
    @Size(min = 3, max = 50)
    private String email;
    private String sdt;
    private String hoten;
    private String gioiTinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngaySinh;
}
