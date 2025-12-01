package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String hoten;
    private String sdt;
    private String email;
    private String tinhThanhPho;
    private String phuongXa;
    private String quanHuyen;
    private String diaChi;

}
