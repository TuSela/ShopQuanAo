package com.Nhom19.shopQuanAo.DTO.Response.Admin;

import com.Nhom19.shopQuanAo.entity.DmCha;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DmConResponse {
    private int id;
    private String name;
//    private DmCha dmCha;
    private String status;

//    @OneToMany(mappedBy = "products")
//    private Set<Products> products = new HashSet<>();

}
