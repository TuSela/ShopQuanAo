package com.Nhom19.shopQuanAo.Controller.Admin;

import com.Nhom19.shopQuanAo.DTO.Request.ImageRequest;
import lombok.Data;

import java.util.List;
@Data
public class CreationProductImageRequest {
    private Integer maSp;
    private Integer maMs;
    private List<ImageRequest> urlImages;
}
