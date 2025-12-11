package com.Nhom19.shopQuanAo.DTO.Request;

import lombok.Data;

import java.util.List;

@Data
public class ImageRequest {
    private List<String> urlImages;
    private boolean daiDien;
}
