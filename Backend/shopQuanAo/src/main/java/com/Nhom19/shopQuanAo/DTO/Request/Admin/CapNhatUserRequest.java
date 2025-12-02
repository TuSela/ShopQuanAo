package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import jakarta.validation.constraints.Size;

public class CapNhatUserRequest {
    private String hoTen;
    private String email;
    private String sdt;

    public CapNhatUserRequest() {
    }


    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

}
