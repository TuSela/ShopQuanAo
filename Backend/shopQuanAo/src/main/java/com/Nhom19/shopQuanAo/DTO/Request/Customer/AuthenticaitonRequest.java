package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthenticaitonRequest {
    private String sdt;
    private String password;

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
