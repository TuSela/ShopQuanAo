package com.Nhom19.shopQuanAo.DTO.Request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthenticaitonRequest {
    private String username;
    private String password;
}
