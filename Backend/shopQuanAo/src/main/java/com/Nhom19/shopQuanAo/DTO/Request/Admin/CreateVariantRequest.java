package com.Nhom19.shopQuanAo.DTO.Request.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.ColorRequest;
import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import com.Nhom19.shopQuanAo.DTO.Request.SizeRequest;
import lombok.Data;

import java.util.List;

@Data
public class CreateVariantRequest {
    private List<ColorRequest> colors;
}
