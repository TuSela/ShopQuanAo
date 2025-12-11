package com.Nhom19.shopQuanAo.DTO.Response.Customer;

import lombok.Data;

import java.util.List;

@Data
public class ColorResponse {
    private int maMs;
    private String tenMs;
    private String urlImages; // lấy 1 ảnh đầu tiên
    private List<ColorSizeResponse> sizes;
}
