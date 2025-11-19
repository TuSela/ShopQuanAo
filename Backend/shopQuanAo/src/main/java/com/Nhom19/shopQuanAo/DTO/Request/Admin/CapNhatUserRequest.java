package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import jakarta.validation.constraints.Size;


public class CapNhatUserRequest {
    @Size(min = 3, max = 50)
    private String username;
    @Size(min = 3, max = 50)
    private String password;
    private String hoTen;
    private String email;
    private String sdt;
    private String diaChi;

    public CapNhatUserRequest() {
    }

    public CapNhatUserRequest(String username, String password, String hoTen, String email, String sdt, String diaChi) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
