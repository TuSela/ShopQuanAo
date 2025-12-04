package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import jakarta.validation.constraints.Size;

public class TaoUsersRequest {
    @Size(min = 3, max = 50)
    private String password;
    @Size(min = 3, max = 50)
    private String email;
    private String sdt;
    private String hoten;
    private String gioiTinh;
    private String ngaySinh;

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public TaoUsersRequest() {
    }

    public TaoUsersRequest(String password, String username, String email, String sdt, String diachi, String hoten) {
        this.password = password;
        this.email = email;
        this.sdt = sdt;
        this.hoten = hoten;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
