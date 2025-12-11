package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ColorResponse {
    private int maMs;
    private String tenMs;
    private String urlImages; // lấy 1 ảnh đầu tiên
    private List<ColorSizeResponse> sizes;
}
