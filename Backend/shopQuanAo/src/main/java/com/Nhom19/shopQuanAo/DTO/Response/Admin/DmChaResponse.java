package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DmChaResponse {
    private int id;
    private String name;
    private String status;
    private Set<DmConResponse> dmCon = new HashSet<>();
}
