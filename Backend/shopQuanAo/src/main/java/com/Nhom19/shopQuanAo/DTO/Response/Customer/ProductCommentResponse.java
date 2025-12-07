package com.Nhom19.shopQuanAo.DTO.Response.Customer;
import com.Nhom19.shopQuanAo.DTO.Response.Admin.UserResponse;
import com.Nhom19.shopQuanAo.DTO.Response.Customer.Home.ProductVariantResponse;
import com.Nhom19.shopQuanAo.entity.ProductVariants;
import com.Nhom19.shopQuanAo.entity.Products;
import com.Nhom19.shopQuanAo.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ProductCommentResponse {
    private int maBl;
    private UserResponse users;
    private ProductVariants productVariants;
    private LocalDateTime ngayTao;
    private Float diemDanhGia;
    private String noiDung;

}
