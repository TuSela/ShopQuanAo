package com.Nhom19.shopQuanAo.DTO.Request;

import lombok.Data;

import java.util.List;
@Data
public class ColorRequest {
    private Integer maMs;
    private List<ImageRequest> urlImages;
    private List<SizeRequest> sizes;
}
