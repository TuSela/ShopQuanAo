package com.Nhom19.shopQuanAo.DTO.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private boolean success;
    private String token;
}
