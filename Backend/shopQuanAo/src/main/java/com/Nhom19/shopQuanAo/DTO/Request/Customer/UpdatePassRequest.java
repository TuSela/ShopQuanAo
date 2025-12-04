package com.Nhom19.shopQuanAo.DTO.Request.Customer;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdatePassRequest {
    private String oldPass;
    private String newPass1;
    private String newPass2;
}
