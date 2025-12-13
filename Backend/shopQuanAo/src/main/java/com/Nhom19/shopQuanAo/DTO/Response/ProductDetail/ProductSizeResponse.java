package com.Nhom19.shopQuanAo.DTO.Response.ProductDetail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductSizeResponse {
    private int maKc;
    private String tenKc;
}
