package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private Integer maTk;
    private String username;
    private String password;
    Set<Role> roles;
}
