package com.Nhom19.shopQuanAo.DTO.Response.Customer.MyCart;

import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductBestSellerResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.ProductDetail.CommentVariantResponse;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyCartItemResponse {
    private ProductBestSellerResponse product;
    private CommentVariantResponse variant;
}
