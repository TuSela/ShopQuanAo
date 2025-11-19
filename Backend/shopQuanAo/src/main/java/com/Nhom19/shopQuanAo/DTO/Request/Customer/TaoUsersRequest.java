package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import jakarta.validation.constraints.Size;


public class TaoUsersRequest {
    @Size(min = 3, max = 50)
    private String password;
    @Size(min = 3, max = 50)
    private String username;
    private String email;
    private String sdt;
    private String diachi;
    private String hoten;

    public TaoUsersRequest() {
    }

    public TaoUsersRequest(String password, String username, String email, String sdt, String diachi, String hoten) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.sdt = sdt;
        this.diachi = diachi;
        this.hoten = hoten;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
